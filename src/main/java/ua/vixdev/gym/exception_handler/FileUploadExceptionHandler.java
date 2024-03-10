package ua.vixdev.gym.exception_handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Handles certain sort of Runtime exceptions.
 */
@Slf4j
@ControllerAdvice
public class FileUploadExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    /**
     * Handles exception which occurs when user tries to upload too big file.
     *
     * @param e exception which happened.
     * @return info that file is too big.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    ErrorResponse handleFileSizeLimitException(RuntimeException e) {
        log.info("Failed to upload file, because it's too big.");
        return new ErrorResponse(List.of(e.getMessage() + String.format(". Max file size is %s", maxFileSize)),
                HttpStatus.PAYLOAD_TOO_LARGE.value(), HttpStatus.PAYLOAD_TOO_LARGE, LocalDateTime.now());
    }

    /**
     * Handles BAD_REQUEST exception.
     *
     * @param e triggered exception.
     * @return info about required params in request.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ErrorResponse handleIncorrectInputDateType(RuntimeException e) {
        log.info("Validation exception: {}", e.getMessage());
        return new ErrorResponse(List.of(e.getMessage().split(":")[1].trim()), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    /**
     * Handles invalid request body (BAD_REQUEST) exception
     * and provides reasons via messages.
     *
     * @param e triggered exception.
     * @return info about what was wrong in request.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorResponse handlerObjectValidationException(MethodArgumentNotValidException e) {
        return new ErrorResponse(e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList(),
                400, HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }
}