package ua.vixdev.gym.user.exceptions.buisnes_logic;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Could not find user with id {" + id + "}!");
    }

}
