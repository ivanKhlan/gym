package ua.vixdev.gym.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ua.vixdev.gym.dto.FileInfoDTO;
import ua.vixdev.gym.exceptions.EmptyFileException;
import ua.vixdev.gym.exceptions.FailedUploadImageException;
import ua.vixdev.gym.service.FileUploadService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class FileController {

    private final FileUploadService imageService;

    /**
     * Saves user file.
     *
     * @param file uploaded from user.
     * @return compressed info about saved file in FileInfoDTO object.
     */
    @PostMapping(consumes = "multipart/form-data", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public FileInfoDTO uploadUserImage(@RequestBody @NotNull(message = "'File' param is required and has to contain some file in it.") MultipartFile file) {
        try {
            return imageService.uploadImageToFileStorage(file);
        } catch (FailedUploadImageException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (EmptyFileException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}