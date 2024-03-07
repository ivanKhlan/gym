package ua.vixdev.gym.exception_handler;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class that gives clear info what went wrong.
 */
public record ErrorResponse(
    List<String> message,
    Integer statusCode,
    HttpStatus httpStatus,
    LocalDateTime time
){}
