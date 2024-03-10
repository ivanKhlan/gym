package ua.vixdev.gym.exceptions;

/**
 * Exception for the case when folder already exists.
 */
public class FolderAlreadyExistsException extends Exception {
    public FolderAlreadyExistsException(String m) {
        super(m);
    }
}
