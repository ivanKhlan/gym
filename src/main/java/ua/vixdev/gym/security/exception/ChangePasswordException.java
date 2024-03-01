package ua.vixdev.gym.security.exception;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-24
 */
public class ChangePasswordException extends RuntimeException{

    public ChangePasswordException() {
        super("The passwords are not the same");
    }
}
