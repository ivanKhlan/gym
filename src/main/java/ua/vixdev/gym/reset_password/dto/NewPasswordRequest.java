package ua.vixdev.gym.reset_password.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordRequest {

    private static final String passwordPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

    @Pattern(regexp = passwordPattern, message = "At last one upper case, lower case, digit, special character and min 8 in length")
    @NotNull(message = "New password can`t be null")
    @NotEmpty(message = "New password can`t be empty")
    private String newPassword;

    @NotNull(message = "Email must not be null")
    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email must not be empty")
    private String email;
}
