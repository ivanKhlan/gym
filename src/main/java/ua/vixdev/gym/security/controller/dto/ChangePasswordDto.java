package ua.vixdev.gym.security.controller.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static ua.vixdev.gym.utils.FieldPatterns.PASSWORD_PATTERN;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-24
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {

    @Pattern(regexp = PASSWORD_PATTERN, message = "{user.password.invalid}")
    private String password;
    private String repeatPassword;
}
