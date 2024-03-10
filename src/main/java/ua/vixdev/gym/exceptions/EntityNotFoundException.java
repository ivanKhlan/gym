package ua.vixdev.gym.exceptions;

/**
 * Exception for the case when failed to find particular entity by id.
 */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
