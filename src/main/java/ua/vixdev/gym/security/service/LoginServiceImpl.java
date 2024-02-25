package ua.vixdev.gym.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ua.vixdev.gym.security.controller.dto.JwtTokenDto;
import ua.vixdev.gym.security.controller.dto.LoginUserDto;
import ua.vixdev.gym.security.controller.dto.RegisterUserDto;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.exceptions.buisnes_logic.UserAlreadyExistsException;
import ua.vixdev.gym.user.repository.UserRepository;

import java.util.Date;

@Component
@Slf4j
public class LoginServiceImpl implements LoginService{

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private long expirationTime;
    private String secret;

    public LoginServiceImpl(AuthenticationManager authenticationManager,
                        UserRepository userRepository,
                        @Value("${jwt.expirationTime}") long expirationTime,
                        @Value("${jwt.secret}") String secret) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.secret = secret;
        this.expirationTime = expirationTime;
    }

    @Override
    public JwtTokenDto login(LoginUserDto loginUserDto) {
        log.debug("Entering in login Method...");
        return new JwtTokenDto(authenticate(loginUserDto.getUsername(), loginUserDto.getPassword()));
    }

    @Override
    public RegisterUserDto register(RegisterUserDto registerUserDto) {
        log.debug("Entering in register Method...");
        if (userRepository.findByEmailAddress(registerUserDto.getEmail()).isPresent()) {
            log.error("User with email: {} is already registered", registerUserDto.getEmail());
            throw new UserAlreadyExistsException(registerUserDto.getEmail());
        }
        userRepository.save(registerUserDto.toUserEntity());
        return null;
    }

    private String authenticate(String username, String password) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails principal = (UserDetails) authenticate.getPrincipal();
        return JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
    }
}
