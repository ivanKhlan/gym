package ua.vixdev.gym.category.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ResponseTypeDto {

    private String value;

    private Boolean visible;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

}
