package ua.vixdev.gym.service.exceptions;

public class ImageIsEmptyOrNullException extends RuntimeException{
    private static final String MESSAGE = "Image can't be empty";
    public ImageIsEmptyOrNullException() {
        super(MESSAGE);
    }
}
