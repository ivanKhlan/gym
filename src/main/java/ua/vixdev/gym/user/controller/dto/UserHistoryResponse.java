package ua.vixdev.gym.user.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
/**
 * Data Transfer Object that represents response of history change
 */
@Data
@Builder
public class UserHistoryResponse {
    private Integer userId;

    private String text;

    private LocalDateTime createdAt;
}
