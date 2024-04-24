package ua.vixdev.gym.status.exceptions;

public class StatusAlreadyExists extends RuntimeException{
    public StatusAlreadyExists(String value){
        super("Status with this value{" + value + "} already existed");
    }
}
