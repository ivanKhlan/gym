package ua.vixdev.gym.application.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing an application.
 */
@Builder
@Getter
public class ApplicationDto {

    @NotBlank
    @NotNull
    private String backEndVersion;
    @NotBlank
    @NotNull
    private String frontEndVersion;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String image;
    @NotBlank
    @NotNull
    private String description;
    @NotBlank
    @NotNull
    private String key;
    @NotBlank
    @NotNull
    private String text;
    @NotBlank
    @NotNull
    private String licenseType;
    @NotNull
    private Long typeId;
}
