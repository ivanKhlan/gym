package ua.vixdev.gym.options.exceptions;

public class OptionNotFoundException extends RuntimeException{
    public OptionNotFoundException(Long id) {
        super("Could not find option with id {" + id + "}!");
    }

}
