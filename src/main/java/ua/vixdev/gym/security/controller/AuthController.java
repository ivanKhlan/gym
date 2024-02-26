package ua.vixdev.gym.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.vixdev.gym.security.controller.dto.JwtTokenDto;
import ua.vixdev.gym.security.controller.dto.LoginUserDto;
import ua.vixdev.gym.security.controller.dto.RegisterUserDto;
import ua.vixdev.gym.security.service.AuthService;
import ua.vixdev.gym.user.entity.UserEntity;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody LoginUserDto loginUserDto) {
        return authService.login(loginUserDto);
    }

    @PostMapping("/register")
    public JwtTokenDto register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return authService.register(registerUserDto);
    }
}
