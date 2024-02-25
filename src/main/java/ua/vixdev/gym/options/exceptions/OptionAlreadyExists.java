package ua.vixdev.gym.options.exceptions;

public class OptionAlreadyExists extends RuntimeException{
    public OptionAlreadyExists(String key){
        super("Option with this key{" + key + "} already existed");
    }
}
