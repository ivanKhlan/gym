package ua.vixdev.gym.upload.exceptions;

/**
 * Exception for the case when folder contains files in context of folder deleting.
 */
public class FolderNotEmptyException extends RuntimeException {
    public FolderNotEmptyException(String message) {
        super(message);
    }
}
