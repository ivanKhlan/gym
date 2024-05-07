package ua.vixdev.gym.options.exceptions;

import ua.vixdev.gym.commons.excetpion.ResourceNotFoundException;

public class OptionNotFoundException extends ResourceNotFoundException {
    public OptionNotFoundException(Long id) {
        super("Option with ID: not found!".formatted(id));
    }

}
