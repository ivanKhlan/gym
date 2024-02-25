package ua.vixdev.gym.security.service;

import ua.vixdev.gym.security.controller.dto.JwtTokenDto;
import ua.vixdev.gym.security.controller.dto.LoginUserDto;
import ua.vixdev.gym.security.controller.dto.RegisterUserDto;

public interface LoginService {

    JwtTokenDto login(LoginUserDto loginUserDto);

    RegisterUserDto register(RegisterUserDto registerUserDto);


}
