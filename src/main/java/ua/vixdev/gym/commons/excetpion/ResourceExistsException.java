package ua.vixdev.gym.commons.excetpion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class ResourceExistsException extends RuntimeException{

    public ResourceExistsException(String msg) {
        super(msg);
    }
}
