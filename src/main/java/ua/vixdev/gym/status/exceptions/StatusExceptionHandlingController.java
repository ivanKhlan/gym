package ua.vixdev.gym.status.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@ControllerAdvice
public class StatusExceptionHandlingController {

    private static final String EMPTY_URI = null;

    @ResponseBody
    @ExceptionHandler(StatusNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse statusNotFoundHandler(StatusNotFoundException exception, HttpServletRequest sr) {
        return bulidErrorResponse(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(), exception.getMessage(), sr.getRequestURI());
    }

    @ResponseBody
    @ExceptionHandler(StatusAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse statusAlreadyExistsHandler(StatusAlreadyExists exception) {
        return bulidErrorResponse(HttpStatus.CONFLICT.name(), HttpStatus.CONFLICT.value(), exception.getMessage(), EMPTY_URI);
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse illegalArgumentHandler(IllegalArgumentException exception) {
        return bulidErrorResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), exception.getMessage(), EMPTY_URI);
    }

    @ResponseBody
    @ExceptionHandler(StatusVisibleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse statusVisibilityHandler(StatusVisibleException exception) {
        return bulidErrorResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), exception.getMessage(), EMPTY_URI);
    }

    private ErrorResponse bulidErrorResponse(String status, int statusCode, String message, String url) {
        return new ErrorResponse(status, statusCode, message, url, LocalDateTime.now());
    }

}

