package ua.vixdev.gym.user.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Data Transfer Object that represents request of history change
 */
@Builder
@Getter
@AllArgsConstructor
public class UserHistoryDto {

    @NotNull
    private Long userId;
    @NotNull
    @NotBlank
    private String text;

}
