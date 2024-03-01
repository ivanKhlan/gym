package ua.vixdev.gym.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.vixdev.gym.security.model.UserRole;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photoUrl;
    private Boolean visible;
    private Set<UserRole> roles = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
