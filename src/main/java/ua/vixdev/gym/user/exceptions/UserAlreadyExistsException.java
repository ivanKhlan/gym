package ua.vixdev.gym.user.exceptions;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-21
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String userEmail) {
        super("User with email: " + userEmail + " already registered!");
    }
}
