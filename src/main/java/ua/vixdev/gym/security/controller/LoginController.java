package ua.vixdev.gym.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.vixdev.gym.security.controller.dto.JwtTokenDto;
import ua.vixdev.gym.security.controller.dto.LoginUserDto;
import ua.vixdev.gym.security.controller.dto.RegisterUserDto;
import ua.vixdev.gym.security.service.LoginService;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody LoginUserDto loginUserDto) {
        return loginService.login(loginUserDto);
    }

    @PostMapping("/register")
    public RegisterUserDto register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return loginService.register(registerUserDto);
    }
}
