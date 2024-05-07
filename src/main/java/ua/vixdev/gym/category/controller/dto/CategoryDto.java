package ua.vixdev.gym.category.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CategoryDto {

    @NotNull
    @NotBlank
    private String value;

    private Boolean visible;

}
