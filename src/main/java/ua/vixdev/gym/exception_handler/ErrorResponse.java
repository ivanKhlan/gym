package ua.vixdev.gym.exception_handler;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;


public record ErrorResponse(
    List<String> message,
    Integer statusCode,
    HttpStatus httpStatus,
    LocalDateTime time
){}
