package ua.vixdev.gym.security.service;

import ua.vixdev.gym.security.controller.dto.JwtTokenDto;
import ua.vixdev.gym.security.controller.dto.LoginUserDto;
import ua.vixdev.gym.security.controller.dto.RegisterUserDto;

public interface AuthenticationService {

    JwtTokenDto login(LoginUserDto loginUserDto);

    JwtTokenDto register(RegisterUserDto registerUserDto);


}
