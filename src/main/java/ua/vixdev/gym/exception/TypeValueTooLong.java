package ua.vixdev.gym.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TypeValueTooLong extends RuntimeException {
    public TypeValueTooLong(String message) {
        super(message);
    }
}
