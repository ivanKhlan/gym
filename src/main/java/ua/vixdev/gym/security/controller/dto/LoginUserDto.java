package ua.vixdev.gym.security.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    @NotBlank(message = "{user.username.notBlank}")
    private String username;
    @NotBlank(message = "{user.password.notBlank}")
    private String password;
}
