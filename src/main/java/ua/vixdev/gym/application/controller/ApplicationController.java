package ua.vixdev.gym.application.controller;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.application.controller.dto.ApplicationDto;
import ua.vixdev.gym.application.entity.ApplicationEntity;
import ua.vixdev.gym.application.service.ApplicationService;

import static org.springframework.http.HttpStatus.*;

/**
 * REST controller for managing applications.
 */
@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

  private static final Long EMPTY_ID = null;
  private final ApplicationService applicationService;

  /**
   * Retrieves an application by its ID.
   *
   * @param id The ID of the application to retrieve.
   * @return The ApplicationDto representing the retrieved application.
   */
  @GetMapping("/{id}")
  public ApplicationEntity findById(@PathVariable Long id) {
    return applicationService.findById(id);
  }

  /**
   * Retrieves all applications.
   *
   * @return List of ApplicationDto representing all applications.
   */
  @GetMapping
  public List<ApplicationEntity> findAll() {
    return applicationService.findAll();
  }

  /**
   * Creates a new application.
   *
   * @param applicationDto The ApplicationDto representing the application to be created.
   * @return The created ApplicationDto.
   */
  @PostMapping
  @ResponseStatus(CREATED)
  public ApplicationEntity createApplication(@RequestBody @Valid ApplicationDto applicationDto) {
    return applicationService.createApplication(mapToApplication(EMPTY_ID, applicationDto));
  }

  /**
   * Updates an existing application.
   *
   * @param applicationDto The updated ApplicationDto.
   * @return The updated ApplicationDto.
   */
  @PutMapping("{id}")
  @ResponseStatus(ACCEPTED)
  public ApplicationEntity updateApplication(Long id, @RequestBody @Valid ApplicationDto applicationDto) {
    return applicationService.updateApplication(mapToApplication(id, applicationDto));
  }

  /**
   * Deletes an application by its ID.
   *
   * @param id The ID of the application to delete.
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(NO_CONTENT)
  public void deleteById(Long id) {
    applicationService.deleteById(id);
  }

  private ApplicationEntity mapToApplication(Long id, ApplicationDto applicationDto) {
    return ApplicationEntity.builder()
            .id(id)
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
}
