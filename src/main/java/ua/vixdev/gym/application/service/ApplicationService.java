package ua.vixdev.gym.application.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.application.entity.ApplicationEntity;
import ua.vixdev.gym.application.exception.ApplicationNotFoundException;
import ua.vixdev.gym.application.exception.TypeNotFoundException;
import ua.vixdev.gym.application.repository.ApplicationRepository;
import ua.vixdev.gym.application.repository.TypeRepository;

/**
 * Service class for managing applications.
 */
@Service
@AllArgsConstructor
public class ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final TypeRepository typeRepository;
  /**
   * Retrieves all applications.
   *
   * @return a list of ApplicationDto representing all applications
   */
  public List<ApplicationEntity> findAll() {
    return applicationRepository.findAll();
  }

  /**
   * Retrieves an application by its ID.
   *
   * @param id the ID of the application to retrieve
   * @return the ApplicationDto object representing the retrieved application
   * @throws ApplicationNotFoundException if no application is found with the given ID
   */
  public ApplicationEntity findById(Long id) {
    return applicationRepository.findById(id)
            .orElseThrow(() -> new ApplicationNotFoundException(id));
  }

  /**
   * Creates a new application.
   *
   * @param application the Application object representing the application to be created
   * @return the ApplicationDto object representing the created application
   */
  public ApplicationEntity createApplication(ApplicationEntity application) {
    return applicationRepository.save(application);
  }

  /**
   * Updates an existing application.
   *
   * @param application the Application object representing the updated application
   * @return the ApplicationDto object representing the updated application
   * @throws ApplicationNotFoundException if no application is found with the ID from the provided
   *                                      ApplicationDto
   */
  public ApplicationEntity updateApplication(ApplicationEntity application) {
    validateApplicationExists(application.getId());
    validateTypeExists(application.getTypeId());
    return applicationRepository.save(application);
  }

  /**
   * Deletes an application.
   *
   * @param id the ID of the application to delete
   * @return the ApplicationDto object representing the deleted application
   * @throws ApplicationNotFoundException if no application is found with the given ID
   */
  public void deleteById(Long id) {
    validateApplicationExists(id);
    applicationRepository.deleteById(id);
  }

 private void validateApplicationExists(Long id) {
   applicationRepository.findById(id)
           .orElseThrow(() -> new ApplicationNotFoundException(id));
 }

  private void validateTypeExists(Long typeId) {
    typeRepository.findById(typeId)
            .orElseThrow(() -> new TypeNotFoundException(typeId));
  }
}
