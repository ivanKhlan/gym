package ua.vixdev.gym.exception;

public class RoleValueIsNullException extends RuntimeException {

    private static final String MESSAGE = "Role is null exception.";
    public RoleValueIsNullException() {
        super(MESSAGE);
    }
}
