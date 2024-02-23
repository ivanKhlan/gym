package ua.vixdev.gym.exceptions;

/**
 * Exception for the case when user doesn't mention any file in request.
 */
public class EmptyFileException extends Exception {
    public EmptyFileException(String message) {
        super(message);
    }
}
