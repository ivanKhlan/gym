package ua.vixdev.gym.security.controller.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static ua.vixdev.gym.utils.FieldPatterns.PASSWORD_PATTERN;

@Getter
public class ChangePasswordDto {

    @Pattern(regexp = PASSWORD_PATTERN, message = "{user.password.invalid}")
    private String password;
    private String repeatPassword;
}
