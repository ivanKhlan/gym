package ua.vixdev.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class RequestCategoryDto {

    private String value;

    private Boolean visible;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;
}
