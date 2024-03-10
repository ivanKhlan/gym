package ua.vixdev.gym.exceptions;

/**
 * Exception for the case when folder contains files in context of folder deleting.
 */
public class FolderNotEmptyException extends Exception {
    public FolderNotEmptyException(String message) {
        super(message);
    }
}
