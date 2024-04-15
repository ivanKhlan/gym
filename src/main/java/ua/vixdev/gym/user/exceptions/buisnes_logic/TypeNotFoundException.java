package ua.vixdev.gym.user.exceptions.buisnes_logic;

public class TypeNotFoundException extends RuntimeException {

    public TypeNotFoundException(Long id) {
        super("Could not find type with id {" + id + "}!");
    }
}
