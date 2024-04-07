package ua.vixdev.gym.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception represents absence of history change in data base
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class HistoryChangesNotFound extends RuntimeException {

    /**
     * Constructor with detailed exception-message
     * @param message - detailed exception message
     */
    public HistoryChangesNotFound(String message) {
        super(message);
    }
}
