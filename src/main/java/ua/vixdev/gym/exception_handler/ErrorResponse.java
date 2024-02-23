package ua.vixdev.gym.exception_handler;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Class that gives clear info what went wrong.
 */
public record ErrorResponse(
    String message,
    Integer statusCode,
    HttpStatus httpStatus,
    LocalDateTime time
){}
