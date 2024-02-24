package ua.vixdev.gym.reset_password.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;

@Data
public class  NewPasswordRequest {

    private String newPassword;

}
