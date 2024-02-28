package ua.vixdev.gym.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.vixdev.gym.security.controller.dto.JwtTokenDto;
import ua.vixdev.gym.security.controller.dto.LoginUserDto;
import ua.vixdev.gym.security.controller.dto.RegisterUserDto;
import ua.vixdev.gym.security.model.GymUserDetails;
import ua.vixdev.gym.security.model.UserRole;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.exceptions.buisnes_logic.UserAlreadyExistsException;
import ua.vixdev.gym.user.exceptions.buisnes_logic.UserNotFoundException;
import ua.vixdev.gym.user.repository.UserRepository;

import java.util.Date;
import java.util.Set;

@Component
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final String PREFIX_BCRYPT = "{bcrypt}";
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private long expirationTime;
    private String secret;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           @Value("${jwt.expirationTime}") long expirationTime,
                           @Value("${jwt.secret}") String secret) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.secret = secret;
        this.expirationTime = expirationTime;
    }

    @Override
    public JwtTokenDto login(LoginUserDto loginUserDto) {
        log.debug("Entering in login Method...");
        return new JwtTokenDto(authenticate(loginUserDto.getUsername(), loginUserDto.getPassword()));
    }

    @Override
    public JwtTokenDto register(RegisterUserDto registerUserDto) {
        log.debug("Entering in register Method...");
        if (userRepository.findByEmailAddress(registerUserDto.getUsername()).isPresent()) {
            log.error("User with email: {} is already registered", registerUserDto.getUsername());
            throw new UserAlreadyExistsException(registerUserDto.getUsername());
        }
        UserEntity savedUser = userRepository.save(new UserEntity(
                registerUserDto.getFirstName(),
                registerUserDto.getLastName(),
                registerUserDto.getUsername(),
                passwordEncoder.encode(registerUserDto.getPassword()),
                registerUserDto.getPhoneNumber(),
                true,
                Set.of(UserRole.ROLE_USER)));
        log.info("Registered user with ID: {}", savedUser.getId());
        return new JwtTokenDto(authenticate(registerUserDto.getUsername(), registerUserDto.getPassword()));
    }

    private String authenticate(String username, String password) {
        UserEntity user = userRepository.findByEmailAddress(username).orElseThrow();
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getId(), password)
        );

        GymUserDetails principal = (GymUserDetails) authenticate.getPrincipal();
        return JWT.create()
                .withSubject(String.valueOf(principal.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
    }
}
