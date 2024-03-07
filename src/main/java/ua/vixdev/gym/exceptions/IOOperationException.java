package ua.vixdev.gym.exceptions;

import java.io.IOException;

/**
 * Exception for the case when uploading file process went wrong.
 */
public class IOOperationException extends IOException {
    public IOOperationException(String message) {
        super(message);
    }
}
