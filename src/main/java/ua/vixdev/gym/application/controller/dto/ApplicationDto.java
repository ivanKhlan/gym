package ua.vixdev.gym.application.controller.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing an application.
 */
@Builder
@Getter
public class ApplicationDto {

  private int id;
  private String backEndVersion;
  private String frontEndVersion;
  private String name;
  private String image;
  private String description;
  private String key;
  private String text;
  private String licenseType;
  private int typeId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;
}
