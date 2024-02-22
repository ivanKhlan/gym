package ua.vixdev.gym.user.exceptions.buisnes_logic;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-21
 */
@ControllerAdvice
public class ExceptionHandlingController {

    private static final String EMPTY_URI = null;

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse userNotFoundHandler(UserNotFoundException exception, HttpServletRequest sr) {
        return bulidErrorResponse(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(), exception.getMessage(), sr.getRequestURI());
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse userAlreadyExistsHandler(UserAlreadyExistsException exception) {
        return bulidErrorResponse(HttpStatus.CONFLICT.name(), HttpStatus.CONFLICT.value(), exception.getMessage(), EMPTY_URI);
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse illegalArgumentHandler(IllegalArgumentException exception) {
        return bulidErrorResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), exception.getMessage(), EMPTY_URI);
    }

    @ResponseBody
    @ExceptionHandler(UserVisibleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse userVisibilityHandler(UserVisibleException exception) {
        return bulidErrorResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), exception.getMessage(), EMPTY_URI);
    }

    private ErrorResponse bulidErrorResponse(String status, int statusCode, String message, String url){
        return new ErrorResponse(status, statusCode, message, url, LocalDateTime.now());
    }
}
