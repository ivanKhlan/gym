package ua.vixdev.gym.status.exceptions;

import ua.vixdev.gym.commons.excetpion.ResourceNotFoundException;

public class StatusNotFoundException extends ResourceNotFoundException {
    public StatusNotFoundException(Long id) {
        super("Status with ID: %s not found!".formatted(id));
    }
}
