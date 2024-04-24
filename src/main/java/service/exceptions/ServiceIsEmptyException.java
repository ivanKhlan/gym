package service.exceptions;

public class ServiceIsEmptyException extends RuntimeException{

    private static final String MESSAGE = "Service is empty or null.";
    public ServiceIsEmptyException() {

        super(MESSAGE);
    }
}
