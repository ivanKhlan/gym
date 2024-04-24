package ua.vixdev.gym.exception;

public class RoleIsEmptyException extends RuntimeException{
    private static final String MESSAGE = "Service is empty or null.";
    public RoleIsEmptyException() {
        super(MESSAGE);
    }
}
