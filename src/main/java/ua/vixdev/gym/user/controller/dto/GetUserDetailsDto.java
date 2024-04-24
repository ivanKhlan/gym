package ua.vixdev.gym.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.vixdev.gym.security.model.UserRole;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDetailsDto extends GetUserDto {
    private LocalDateTime emailVerifiedAt;
    private String rememberToken;
    private String phoneNumber;
    private LocalDateTime deletedAt;

    public GetUserDetailsDto(Long id, String firstName, String lastName, String email, String password, String photoUrl,
                             Boolean visible, Set<UserRole> roles, LocalDateTime createdAt, LocalDateTime updatedAt,
                             String rememberToken, LocalDateTime emailVerifiedAt, String phoneNumber, LocalDateTime deletedAt) {
        super(id, firstName, lastName, email, password, photoUrl, visible, roles, createdAt, updatedAt);
        this.rememberToken = rememberToken;
        this.emailVerifiedAt = emailVerifiedAt;
        this.phoneNumber = phoneNumber;
        this.deletedAt = deletedAt;
    }
}
