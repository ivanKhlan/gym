package ua.vixdev.gym.upload.exceptions;

/**
 * Exception for the case when uploading file process went wrong.
 */
public class IOOperationException extends RuntimeException {
    public IOOperationException(String message) {
        super(message);
    }
}
