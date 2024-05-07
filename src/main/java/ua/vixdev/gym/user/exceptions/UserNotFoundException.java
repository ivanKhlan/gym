package ua.vixdev.gym.user.exceptions;

import ua.vixdev.gym.commons.excetpion.ResourceNotFoundException;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-21
 */
public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(Long id) {
        super("User with ID: %s not found!".formatted(id));
    }

}
