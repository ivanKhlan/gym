package ua.vixdev.gym.upload.exceptions;

/**
 * Exception for the case when folder already exists.
 */
public class FolderAlreadyExistsException extends RuntimeException {
    public FolderAlreadyExistsException(String m) {
        super(m);
    }
}
