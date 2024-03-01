package ua.vixdev.gym.security.service;

import ua.vixdev.gym.security.controller.dto.ChangePasswordDto;

public interface ResetPasswordService {

    void changePassword(Long id, ChangePasswordDto changePassword);
}
