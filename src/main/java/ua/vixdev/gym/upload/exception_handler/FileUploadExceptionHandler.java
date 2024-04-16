package ua.vixdev.gym.upload.exception_handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import ua.vixdev.gym.upload.exceptions.*;

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
     * Handles invalid request body (BAD_REQUEST) exception.
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

    /**
     * Handles failed attempt to convert JSON into POJO.
     *
     * @param e triggered exception.
     * @return info about what was wrong in request.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ErrorResponse handlerBadParamTypesException(HttpMessageNotReadableException e) {
        String errMsg = e.getMessage().substring(e.getMessage().indexOf('`'), e.getMessage().lastIndexOf('`') + 1);
        errMsg += " supports " + e.getMessage().substring(e.getMessage().lastIndexOf(':') + 1);

        return new ErrorResponse(List.of(errMsg),400, HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    /**
     * Handles exception occurred due to lack of source existence.
     *
     * @param e triggered exception.
     * @return info about what was wrong in request.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        log.info("Entity not found. Reason: {}", e.getMessage());
        return new ErrorResponse(List.of(e.getMessage()), 404, HttpStatus.NOT_FOUND, LocalDateTime.now());
    }

    /**
     * Handles failed IO operation.
     *
     * @param e triggered exception.
     * @return info about what was wrong in request.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(IOOperationException.class)
    ErrorResponse handleIoOperationException(IOOperationException e) {
        log.error("IO Exception occurred. Reason: %s".formatted(e.getCause()));
        return new ErrorResponse(List.of(e.getMessage()), 503, HttpStatus.SERVICE_UNAVAILABLE, LocalDateTime.now());
    }

    /**
     * Handles exception when user tries to send an empty file.
     *
     * @param e triggered exception.
     * @return info about what was wrong in request.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyFileException.class)
    ErrorResponse handleEmptyFileException(EmptyFileException e) {
        log.info("Empty file. {}", e.getMessage());
        return new ErrorResponse(List.of(e.getMessage()), 400, HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    /**
     * Handles situation when folder with desired name already exists.
     *
     * @param e triggered exception.
     * @return info about what was wrong in request.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(FolderAlreadyExistsException.class)
    ErrorResponse handleFolderAlreadyExistsException(FolderAlreadyExistsException e) {
        log.info("Seems like folder already exists. {}", e.getMessage());
        return new ErrorResponse(List.of(e.getMessage()), 409, HttpStatus.CONFLICT, LocalDateTime.now());
    }

    /**
     * Handles failed attempt to rename folder.
     *
     * @param e triggered exception.
     * @return info about what was wrong in request.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(FailedRenameFolderException.class)
    ErrorResponse handleFailedRenameFolderException(FailedRenameFolderException e) {
        log.info("Failed to rename folder. {}", e.getMessage());
        return new ErrorResponse(List.of(e.getMessage()), 409, HttpStatus.CONFLICT, LocalDateTime.now());
    }

    /**
     * Handles situation when folder to delete is not empty.
     *
     * @param e triggered exception.
     * @return info about what was wrong in request.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(FolderNotEmptyException.class)
    ErrorResponse handleFolderNotEmptyException(FolderNotEmptyException e) {
        log.info("Failed to delete folder -> not empty. {}", e.getMessage());
        return new ErrorResponse(List.of(e.getMessage()), 409, HttpStatus.CONFLICT, LocalDateTime.now());
    }
}