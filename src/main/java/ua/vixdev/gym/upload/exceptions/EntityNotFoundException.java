package ua.vixdev.gym.upload.exceptions;

/**
 * Exception for the case when failed to find particular entity by id.
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
