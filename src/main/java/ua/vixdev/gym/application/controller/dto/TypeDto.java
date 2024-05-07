package ua.vixdev.gym.application.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class TypeDto {

    @NotNull
    @NotBlank
    private String value;

    private Boolean visible;
}
