package ua.vixdev.gym.exceptions;

import java.io.IOException;

/**
 * Exception for the case when uploading file process went wrong.
 */
public class FailedUploadImageException extends IOException {
    public FailedUploadImageException(String message) {
        super(message);
    }
}
