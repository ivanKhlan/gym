package ua.vixdev.gym.security.service;

import ua.vixdev.gym.security.controller.dto.JwtTokenDto;
import ua.vixdev.gym.security.controller.dto.LoginUserDto;
import ua.vixdev.gym.security.controller.dto.RegisterUserDto;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-24
 */
public interface AuthenticationService {

    JwtTokenDto login(LoginUserDto loginUserDto);

    JwtTokenDto register(RegisterUserDto registerUserDto);


}
