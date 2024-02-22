package ua.vixdev.gym.user.exceptions.buisnes_logic;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-21
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Could not find user with id {" + id + "}!");
    }

}
