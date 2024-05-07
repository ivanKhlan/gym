package ua.vixdev.gym.user.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UserRoleDto {

    @NotBlank
    @NotNull
    private String value;
    private Boolean visible;
}
