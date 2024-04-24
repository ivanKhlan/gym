package ua.vixdev.gym.application.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.application.controller.dto.ApplicationDto;
import ua.vixdev.gym.application.entity.Application;
import ua.vixdev.gym.exception.ApplicationNotFoundException;
import ua.vixdev.gym.application.mapper.ApplicationMapper;
import ua.vixdev.gym.application.repository.ApplicationRepository;

/**
 * Service class for managing applications.
 */
@Service
@AllArgsConstructor
public class ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final ApplicationMapper applicationMapper;

  /**
   * Retrieves all applications.
   *
   * @return a list of ApplicationDto representing all applications
   */
  public List<ApplicationDto> getAllApplications() {
    return applicationRepository.findAllByDeletedAtIsNull()
        .stream()
        .map(applicationMapper::toDto)
        .toList();
  }

  /**
   * Creates a new application.
   *
   * @param applicationDto the ApplicationDto object representing the application to be created
   * @return the ApplicationDto object representing the created application
   */
  public ApplicationDto createApplication(ApplicationDto applicationDto) {
    Application application = applicationMapper.createFromDto(applicationDto);
    return applicationMapper.toDto(applicationRepository.save(application));
  }

  /**
   * Retrieves an application by its ID.
   *
   * @param id the ID of the application to retrieve
   * @return the ApplicationDto object representing the retrieved application
   * @throws ApplicationNotFoundException if no application is found with the given ID
   */
  public ApplicationDto getApplicationById(int id) {
    Application application = applicationRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(
            () -> new ApplicationNotFoundException("Application not found with id: " + id));
    return applicationMapper.toDto(application);
  }

  /**
   * Updates an existing application.
   *
   * @param applicationDto the ApplicationDto object representing the updated application
   * @return the ApplicationDto object representing the updated application
   * @throws ApplicationNotFoundException if no application is found with the ID from the provided
   *                                      ApplicationDto
   */
  public ApplicationDto updateApplication(ApplicationDto applicationDto) {
    int id = applicationDto.getId();

    Application application = applicationRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(() -> new ApplicationNotFoundException("User not found with id: " + id));

    applicationMapper.updateFromDto(applicationDto, application);
    return applicationMapper.toDto(applicationRepository.save(application));
  }

  /**
   * Deletes an application.
   *
   * @param id the ID of the application to delete
   * @return the ApplicationDto object representing the deleted application
   * @throws ApplicationNotFoundException if no application is found with the given ID
   */
  public ApplicationDto deleteApplication(int id) {
    Application application = applicationRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(() -> new ApplicationNotFoundException("User not found with id: " + id));

    applicationMapper.softDelete(application);
    return applicationMapper.toDto(applicationRepository.save(application));
  }
}
