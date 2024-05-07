package ua.vixdev.gym.user.exceptions;

import ua.vixdev.gym.commons.excetpion.ResourceNotFoundException;

public class RoleNotFoundException extends ResourceNotFoundException {

    public RoleNotFoundException(Long id) {
        super("Role with ID: %s not found!".formatted(id));
    }
}
