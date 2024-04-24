package ua.vixdev.gym.exception;

public class RoleValueIsEmptyException extends RuntimeException{

    private static final String MESSAGE = "Role value is empty.";
    public RoleValueIsEmptyException() {
        super(MESSAGE);
    }
}
