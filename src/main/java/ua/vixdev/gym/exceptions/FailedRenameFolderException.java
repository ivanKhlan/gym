package ua.vixdev.gym.exceptions;

/**
 * Exception for the case when updating folder name went wrong.
 */
public class FailedRenameFolderException extends Exception {
    public FailedRenameFolderException(String message) {
        super(message);
    }
}
