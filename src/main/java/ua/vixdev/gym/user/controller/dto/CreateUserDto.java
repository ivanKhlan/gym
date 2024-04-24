package ua.vixdev.gym.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.vixdev.gym.security.model.UserRole;

import java.util.Set;

import static ua.vixdev.gym.commons.utils.FieldPatterns.*;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    @NotBlank(message = "{user.firstName.notBlank}")
    @Size(min = 2, max = 70, message = "{user.firstName.invalid}")
    private String firstName;
    @NotBlank(message = "{user.lastName.notBlank}")
    @Size(min = 2, max = 70, message = "{user.lastName.invalid}")
    private String lastName;
    @Email(regexp = EMAIL_PATTERN, message = "{user.email.invalid}")
    private String email;
    @Pattern(regexp = PASSWORD_PATTERN, message = "{user.password.invalid}")
    private String password;
    @Pattern(regexp = PHONE_PATTERN, message = "{user.phoneNumber.invalid}")
    private String phoneNumber;
    private Boolean visible;
    private Set<UserRole> roles;


}
