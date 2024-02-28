package ua.vixdev.gym.security.service;

import ua.vixdev.gym.security.controller.dto.JwtTokenDto;
import ua.vixdev.gym.security.controller.dto.LoginUserDto;
import ua.vixdev.gym.security.controller.dto.RegisterUserDto;
import ua.vixdev.gym.user.entity.UserEntity;

public interface AuthService {

    JwtTokenDto login(LoginUserDto loginUserDto);

    JwtTokenDto register(RegisterUserDto registerUserDto);


}
