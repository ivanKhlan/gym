package ua.vixdev.gym.options.exceptions;

public class OptionVisibleException extends RuntimeException{
    public OptionVisibleException(String visible){

        super("Unknown visibility value: " + visible + "!");
    }
}
