package ua.vixdev.gym.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.vixdev.gym.security.controller.dto.JwtTokenDto;
import ua.vixdev.gym.security.controller.dto.LoginUserDto;
import ua.vixdev.gym.security.controller.dto.RegisterUserDto;
import ua.vixdev.gym.security.model.UserEntityDetails;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.entity.UserRoleEntity;
import ua.vixdev.gym.user.exceptions.UserAlreadyExistsException;
import ua.vixdev.gym.user.repository.UserRepository;
import ua.vixdev.gym.user.repository.UserRoleRepository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-24
 */
@Component
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final long expirationTime;
    private final String secret;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     UserRepository userRepository,
                                     UserRoleRepository roleRepository, PasswordEncoder passwordEncoder,
                                     @Value("${jwt.expirationTime}") long expirationTime,
                                     @Value("${jwt.secret}") String secret) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        if (userRepository.findByEmail(registerUserDto.getUsername()).isPresent()) {
            log.warn("User with email: {} is already registered", registerUserDto.getUsername());
            throw new UserAlreadyExistsException(registerUserDto.getUsername());
        }
        var roleUser = roleRepository.findByValue("ROLE_USER").orElseThrow();
        var user = UserEntity.builder()
                .firstName(registerUserDto.getFirstName())
                .lastName(registerUserDto.getLastName())
                .email(registerUserDto.getUsername())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .phoneNumber(registerUserDto.getPhoneNumber())
                .roles(Set.of(roleUser))
                .visible(true)
                .build();
        userRepository.save(user);
        log.info("Registered user with ID: {}", user.getId());
        return new JwtTokenDto(authenticate(registerUserDto.getUsername(), registerUserDto.getPassword()));
    }

    /**
     * This method is used to create a token.
     *
     * @param username User's email
     * @param password User's password
     * @return Returns a token.
     */
    private String authenticate(String username, String password) {
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("The User: %s, is not registered!".formatted(username)));
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getId(), password)
        );

        UserEntityDetails principal = (UserEntityDetails) authenticate.getPrincipal();
        return JWT.create()
                .withSubject(String.valueOf(principal.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
    }
}
