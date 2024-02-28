package ua.vixdev.gym;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.vixdev.gym.user.exceptions.ErrorResponse;
import ua.vixdev.gym.user.exceptions.UserAlreadyExistsException;
import ua.vixdev.gym.user.exceptions.UserNotFoundException;
import ua.vixdev.gym.user.exceptions.UserVisibleException;
import ua.vixdev.gym.user.exceptions.ValidationErrorResponse;
import ua.vixdev.gym.user.exceptions.Violation;

import java.time.LocalDateTime;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-28
 */
public class GymExceptionHandling {

    private static final String EMPTY_URI = null;

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse userNotFoundHandler(UserNotFoundException exception, HttpServletRequest sr) {
        return getErrorResponse(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(), exception.getMessage(), sr.getRequestURI());
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse userAlreadyExistsHandler(UserAlreadyExistsException exception) {
        return getErrorResponse(HttpStatus.CONFLICT.name(), HttpStatus.CONFLICT.value(), exception.getMessage(), EMPTY_URI);
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse illegalArgumentHandler(IllegalArgumentException exception) {
        return getErrorResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), exception.getMessage(), EMPTY_URI);
    }

    @ResponseBody
    @ExceptionHandler(UserVisibleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userVisibilityHandler(UserVisibleException exception) {
        return getErrorResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), exception.getMessage(), EMPTY_URI);
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse accessDeniedHandler(AccessDeniedException exception) {
        return getErrorResponse(HttpStatus.FORBIDDEN.name(), HttpStatus.FORBIDDEN.value(), exception.getMessage(), EMPTY_URI);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            error.getViolations().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse methodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return error;
    }

    private ErrorResponse getErrorResponse(String status, int statusCode, String message, String url){
        return new ErrorResponse(status, statusCode, message, url, LocalDateTime.now());
    }
}
