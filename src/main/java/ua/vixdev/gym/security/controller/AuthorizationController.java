package ua.vixdev.gym.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.vixdev.gym.security.controller.dto.JwtTokenDto;
import ua.vixdev.gym.security.controller.dto.LoginUserDto;
import ua.vixdev.gym.security.controller.dto.RegisterUserDto;
import ua.vixdev.gym.security.service.AuthenticationService;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-23
 */
@RestController
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody LoginUserDto loginUserDto) {
        return authenticationService.login(loginUserDto);
    }

    @PostMapping("/register")
    public JwtTokenDto register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return authenticationService.register(registerUserDto);
    }
}
