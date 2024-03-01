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

import static ua.vixdev.gym.utils.FieldPatterns.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    @NotBlank(message = "{user.firstName.notBlank}")
    @Size(min = 2, max = 70, message = "{user.firstName.invalid}")
    private String firstName;
    @NotBlank(message = "{user.lastName.notBlank}")
    @Size(min = 2, max = 70, message = "{user.lastName.invalid}")
    private String lastName;
    @Email(regexp = EMAIL_PATTERN, message = "{user.email.invalid}")
    private String email;
    @Pattern(regexp = PHONE_PATTERN, message = "{user.phoneNumber.invalid}")
    private String phoneNumber;
    private Boolean visible;
    private Set<UserRole> roles;
}
