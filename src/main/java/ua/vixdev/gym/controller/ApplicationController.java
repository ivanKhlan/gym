package ua.vixdev.gym.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.vixdev.gym.dto.ApplicationDto;
import ua.vixdev.gym.service.ApplicationService;

/**
 * REST controller for managing applications.
 */
@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

  private final ApplicationService applicationService;

  /**
   * Retrieves all applications.
   *
   * @return List of ApplicationDto representing all applications.
   */
  @GetMapping
  public List<ApplicationDto> getAllApplications() {
    return applicationService.getAllApplications();
  }

  /**
   * Creates a new application.
   *
   * @param applicationDto The ApplicationDto representing the application to be created.
   * @return The created ApplicationDto.
   */
  @PostMapping
  public ApplicationDto createApplication(@RequestBody ApplicationDto applicationDto) {
    return applicationService.createApplication(applicationDto);
  }

  /**
   * Retrieves an application by its ID.
   *
   * @param id The ID of the application to retrieve.
   * @return The ApplicationDto representing the retrieved application.
   */
  @GetMapping("/{id}")
  public ApplicationDto getApplicationById(@PathVariable int id) {
    return applicationService.getApplicationById(id);
  }

  /**
   * Updates an existing application.
   *
   * @param updatedApplicationDto The updated ApplicationDto.
   * @return The updated ApplicationDto.
   */
  @PutMapping
  public ApplicationDto updateApplication(@RequestBody ApplicationDto updatedApplicationDto) {
    return applicationService.updateApplication(updatedApplicationDto);
  }

  /**
   * Deletes an application by its ID.
   *
   * @param id The ID of the application to delete.
   */
  @DeleteMapping("/{id}")
  public ApplicationDto deleteApplication(@PathVariable int id) {
    return applicationService.deleteApplication(id);
  }
}
