package ua.vixdev.gym.reset_password.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordRequest {

    private String newPassword;
    private String email;
}
