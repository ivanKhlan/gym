package ua.vixdev.gym.exception;

public class RoleNotFoundException extends RuntimeException{

    private final static String MESSAGE = "Failed to find service: service not found in the database.";
    public RoleNotFoundException() {
        super(MESSAGE);
    }
}
