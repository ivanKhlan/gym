package ua.vixdev.gym.security.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-23
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenDto {
    private String token;
}
