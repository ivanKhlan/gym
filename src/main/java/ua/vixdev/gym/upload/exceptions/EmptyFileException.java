package ua.vixdev.gym.upload.exceptions;

/**
 * Exception for the case when user doesn't mention any file in request.
 */
public class EmptyFileException extends RuntimeException {
    public EmptyFileException(String message) {
        super(message);
    }
}
