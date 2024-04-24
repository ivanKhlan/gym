package ua.vixdev.gym.service.exceptions;

public class ServiceNotFoundException extends RuntimeException{
    private final static String MESSAGE = "Failed to find service: service not found in the database.";
    public ServiceNotFoundException() {
        super(MESSAGE);
    }
}
