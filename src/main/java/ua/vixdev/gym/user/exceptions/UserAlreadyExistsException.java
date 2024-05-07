package ua.vixdev.gym.user.exceptions;

import ua.vixdev.gym.commons.excetpion.ResourceExistsException;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-21
 */
public class UserAlreadyExistsException extends ResourceExistsException {
    public UserAlreadyExistsException(String userEmail) {
        super("User with email: " + userEmail + " already exists!");
    }
}
