package ua.vixdev.gym.user.exceptions.buisnes_logic;

public class GenderNotFoundException extends RuntimeException {

    public GenderNotFoundException(Long id) {
        super("Could not find gender with id {" + id + "}!");
    }
}
