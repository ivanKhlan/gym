package ua.vixdev.gym.options.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(String status, int statusCode, String message, String path, LocalDateTime timestamp) {

}
