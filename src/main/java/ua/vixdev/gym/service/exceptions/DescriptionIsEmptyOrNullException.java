package ua.vixdev.gym.service.exceptions;

public class DescriptionIsEmptyOrNullException extends RuntimeException {

    private static final String MESSAGE = "Description can't be empty.";
    public DescriptionIsEmptyOrNullException() {
        super(MESSAGE);
    }
}
