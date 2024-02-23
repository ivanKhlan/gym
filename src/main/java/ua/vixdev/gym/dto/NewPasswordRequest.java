package ua.vixdev.gym.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class NewPasswordRequest {
    @NonNull
    private String token;
    private String newPassword;

}
