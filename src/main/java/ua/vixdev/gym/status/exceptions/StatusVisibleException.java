package ua.vixdev.gym.status.exceptions;

public class StatusVisibleException extends RuntimeException{
    public StatusVisibleException(String visible){
        super("Unknown visibility value: " + visible + "!");
    }
}
