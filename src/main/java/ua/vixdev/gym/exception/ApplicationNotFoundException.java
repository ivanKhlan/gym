package ua.vixdev.gym.exception;

/**
 * Custom exception class for representing an application not found scenario.
 */
public class ApplicationNotFoundException extends RuntimeException {

  /**
   * Constructs a new ApplicationNotFoundException with the specified detail message.
   *
   * @param message The detail message.
   */
  public ApplicationNotFoundException(String message) {
    super(message);
  }
}
