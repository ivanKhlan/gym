package ua.vixdev.gym.security.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    @NotBlank(message = "{user.username.notBlank}")
    private String username;
    @NotBlank(message = "{user.password.notBlank}")
    private String password;
}
