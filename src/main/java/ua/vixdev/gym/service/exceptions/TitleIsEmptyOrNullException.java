package ua.vixdev.gym.service.exceptions;

public class TitleIsEmptyOrNullException extends RuntimeException{

    private static final String MESSAGE = "Title can't be empty.";
    public TitleIsEmptyOrNullException() {
        super(MESSAGE);
    }
}
