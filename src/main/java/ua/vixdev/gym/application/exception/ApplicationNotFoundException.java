package ua.vixdev.gym.application.exception;

import ua.vixdev.gym.commons.excetpion.ResourceNotFoundException;

/**
 * Custom exception class for representing an application not found scenario.
 */
public class ApplicationNotFoundException extends ResourceNotFoundException {

  /**
   * Constructs a new ApplicationNotFoundException with the specified detail message.
   *
   * @param id The ID App.
   */
  public ApplicationNotFoundException(Long id) {
    super("Application with ID: %s not found!".formatted(id));
  }
}
