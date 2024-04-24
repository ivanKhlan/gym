package ua.vixdev.gym.category.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class RequestTypeDto {

    private String value;

    private Boolean visible;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;
}
