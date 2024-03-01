package ua.vixdev.gym.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    /**
     * This method is used for user login.
     *
     * @param loginUserDto This parameter represents the user's credentials.
     * @return Returns a token for the user with status 200(Ok).
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtTokenDto login(@RequestBody LoginUserDto loginUserDto) {
        return authenticationService.login(loginUserDto);
    }

    /**
     * This method is used for user register.
     *
     * @param registerUserDto This parameter represents the user's credentials.
     * @return Returns a token for the user with status 200(Ok).
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public JwtTokenDto register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return authenticationService.register(registerUserDto);
    }
}
