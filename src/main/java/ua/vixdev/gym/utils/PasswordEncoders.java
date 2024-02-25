package ua.vixdev.gym.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoders {

    private static final String PREFIX = "{bcrypt}";

    private PasswordEncoders() {
        throw new UnsupportedOperationException();
    }

    public static String encodePassword(String password) {
        return PREFIX + new BCryptPasswordEncoder().encode(password);
    }
}
