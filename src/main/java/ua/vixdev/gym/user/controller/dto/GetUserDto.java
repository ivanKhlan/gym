package ua.vixdev.gym.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.vixdev.gym.security.model.UserRole;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime emailVerifiedAt;
    private String password;
    private String rememberToken;
    private String photoUrl;
    private String phoneNumber;
    private Boolean visible;
    private Set<UserRole> roles = new HashSet<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
