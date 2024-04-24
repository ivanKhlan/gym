package ua.vixdev.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a role in the gym application.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleDto {

    private String value;

    private Boolean visible;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
