package ua.vixdev.gym.user.exceptions.buisnes_logic;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String userEmail) {
        super("User with email: {" + userEmail + "} already registered!");
    }
}
