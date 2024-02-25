package ua.vixdev.gym.status.exceptions;

public class StatusNotFoundException extends RuntimeException{
    public StatusNotFoundException(Long id) {
        super("Could not find status with id {" + id + "}!");
    }
}
