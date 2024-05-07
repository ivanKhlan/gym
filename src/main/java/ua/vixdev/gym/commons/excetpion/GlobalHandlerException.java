package ua.vixdev.gym.commons.excetpion;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.security.exception.ChangePasswordException;
import ua.vixdev.gym.user.exceptions.UserAlreadyExistsException;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-28
 */

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorResponse userNotFoundHandler(MethodArgumentNotValidException e) {
        return buildResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    ErrorResponse userNotFoundHandler(BusinessException e) {
        return buildResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    ErrorResponse userNotFoundHandler(ResourceNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(ResourceExistsException.class)
    ErrorResponse userAlreadyExistsHandler(ResourceExistsException e) {
        return buildResponse(HttpStatus.CONFLICT.name(), HttpStatus.CONFLICT.value(), e.getMessage());
    }

    private ErrorResponse buildResponse(String status, int statusCode, String message){
        return new ErrorResponse(status, statusCode, message, new Date());
    }
}
