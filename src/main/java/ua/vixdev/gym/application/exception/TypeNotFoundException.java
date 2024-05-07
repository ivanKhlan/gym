package ua.vixdev.gym.application.exception;

import ua.vixdev.gym.commons.excetpion.ResourceNotFoundException;

public class TypeNotFoundException extends ResourceNotFoundException {
    public TypeNotFoundException(Long id) {
        super("Type with ID: %s not found!".formatted(id));
    }
}
