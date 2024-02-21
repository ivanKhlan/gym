package ua.vixdev.gym.user.exceptions.buisnes_logic;

public class UserVisibleException extends RuntimeException {
    public UserVisibleException(String value) {
        super("Unknown visibility value: " + value + "!");
    }
}
