package ua.vixdev.gym.security.exception;

public class ChangePasswordException extends RuntimeException{

    public ChangePasswordException() {
        super("The passwords are not the same");
    }
}
