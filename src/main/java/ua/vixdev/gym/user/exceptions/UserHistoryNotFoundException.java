package ua.vixdev.gym.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.vixdev.gym.commons.excetpion.ResourceNotFoundException;

/**
 * Custom exception represents absence of history change in data base
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserHistoryNotFoundException extends ResourceNotFoundException {

    /**
     * Constructor with detailed exception-message
     * @param message - detailed exception message
     */
    public UserHistoryNotFoundException(Long id) {
        super("User History with ID: %s not found!".formatted(id));
    }
}
