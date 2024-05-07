package ua.vixdev.gym.options.exceptions;

import ua.vixdev.gym.commons.excetpion.ResourceExistsException;

public class OptionAlreadyExists extends ResourceExistsException {
    public OptionAlreadyExists(String key){
        super("Option with KEY: %s already existed".formatted(key));
    }
}
