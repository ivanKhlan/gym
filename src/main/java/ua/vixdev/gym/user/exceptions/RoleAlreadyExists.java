package ua.vixdev.gym.user.exceptions;

import ua.vixdev.gym.commons.excetpion.ResourceExistsException;

public class RoleAlreadyExists extends ResourceExistsException {
    public RoleAlreadyExists(String value) {
        super("Role value: %s already exists!".formatted(value));
    }
}
