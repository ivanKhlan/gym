package ua.vixdev.gym.user.exceptions.buisnes_logic;

import java.time.LocalDateTime;

public record ErrorResponse(String status, int statusCode, String message, String path, LocalDateTime timestamp) {

}
