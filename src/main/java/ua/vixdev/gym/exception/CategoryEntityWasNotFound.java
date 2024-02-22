package ua.vixdev.gym.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryEntityWasNotFound extends RuntimeException {
    public CategoryEntityWasNotFound(String message) {
        super(message);
    }
}
