package ua.vixdev.gym.user.exceptions;

import java.time.LocalDateTime;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-21
 */
public record ErrorResponse(String status, int statusCode, String message, String path, LocalDateTime timestamp) {

}
