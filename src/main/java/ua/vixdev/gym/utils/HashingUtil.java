package ua.vixdev.gym.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;

public class HashingUtil {

    /**
     * Creates md5 hash according to the current datetime.
     * Used for creating new name of uploaded file.
     *
     * @return md5 hashed value of current time.
     */
    public static String createHashedFileName() {
        return DigestUtils.md5Hex(LocalDateTime.now().toString());
    }
}
