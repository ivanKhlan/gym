package ua.vixdev.gym.application.mapper;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import ua.vixdev.gym.application.controller.dto.ApplicationDto;
import ua.vixdev.gym.application.entity.Application;

/**
 * Mapper class for converting between Application and ApplicationDto.
 */
@Component
public class ApplicationMapper {

  /**
   * Creates an Application entity from an ApplicationDto.
   *
   * @param applicationDto The ApplicationDto to convert.
   * @return The created Application entity.
   */
  public Application createFromDto(ApplicationDto applicationDto) {
    if (applicationDto == null) {
      return null;
    }

    return Application.builder()
        .id(applicationDto.getId())
        .backEndVersion(applicationDto.getBackEndVersion())
        .frontEndVersion(applicationDto.getFrontEndVersion())
        .name(applicationDto.getName())
        .image(applicationDto.getImage())
        .description(applicationDto.getDescription())
        .key(applicationDto.getKey())
        .text(applicationDto.getText())
        .licenseType(applicationDto.getLicenseType())
        .typeId(applicationDto.getTypeId())
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();
  }

  /**
   * Updates an Application entity from an ApplicationDto.
   *
   * @param applicationDto The ApplicationDto containing updated information.
   * @param application    The Application entity to update.
   */
  public void updateFromDto(ApplicationDto applicationDto, Application application) {
    if (applicationDto != null) {
      application.setId(applicationDto.getId());
      application.setBackEndVersion(applicationDto.getBackEndVersion());
      application.setFrontEndVersion(applicationDto.getFrontEndVersion());
      application.setName(applicationDto.getName());
      application.setImage(applicationDto.getImage());
      application.setDescription(applicationDto.getDescription());
      application.setKey(applicationDto.getKey());
      application.setLicenseType(applicationDto.getLicenseType());
      application.setTypeId(applicationDto.getTypeId());
      application.setUpdatedAt(LocalDateTime.now());
    }
  }

  /**
   * Soft deletes an application by setting the deletedAt timestamp to the current time.
   *
   * @param application the Application object to soft delete
   */
  public void softDelete(Application application) {
    application.setDeletedAt(LocalDateTime.now());
  }

  /**
   * Converts an Application entity to an ApplicationDto.
   *
   * @param application The Application entity to convert.
   * @return The created ApplicationDto.
   */
  public ApplicationDto toDto(Application application) {
    if (application == null) {
      return null;
    }

    return ApplicationDto.builder()
        .id(application.getId())
        .backEndVersion(application.getBackEndVersion())
        .frontEndVersion(application.getFrontEndVersion())
        .name(application.getName())
        .image(application.getImage())
        .description(application.getDescription())
        .key(application.getKey())
        .text(application.getText())
        .licenseType(application.getLicenseType())
        .typeId(application.getTypeId())
        .createdAt(application.getCreatedAt())
        .updatedAt(application.getUpdatedAt())
        .deletedAt(application.getDeletedAt())
        .build();
  }
}
