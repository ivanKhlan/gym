package ua.vixdev.gym.status.exceptions;

import ua.vixdev.gym.commons.excetpion.ResourceExistsException;

public class StatusAlreadyExists extends ResourceExistsException {
    public StatusAlreadyExists(String value){
        super("Status with this value{" + value + "} already existed");
    }
}
