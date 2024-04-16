package ua.vixdev.gym.upload.exceptions;

/**
 * Exception for the case when updating folder name went wrong.
 */
public class FailedRenameFolderException extends RuntimeException {
    public FailedRenameFolderException(String message) {
        super(message);
    }
}
