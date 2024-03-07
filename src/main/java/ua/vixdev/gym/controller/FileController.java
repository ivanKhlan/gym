package ua.vixdev.gym.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ua.vixdev.gym.dto.FileRepresentationDTO;
import ua.vixdev.gym.dto.FileInfoDTO;
import ua.vixdev.gym.dto.VisionLevelDTO;
import ua.vixdev.gym.exceptions.EmptyFileException;
import ua.vixdev.gym.exceptions.IOOperationException;
import ua.vixdev.gym.exceptions.EntityNotFoundException;
import ua.vixdev.gym.service.FileService;

import java.util.List;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/files/")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("{fileId}")
    @ResponseStatus(HttpStatus.OK)
    public FileRepresentationDTO obtainFileById(@PathVariable Long fileId) {
        try {
            return fileService.obtainFileById(fileId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOOperationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("all/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public List<FileRepresentationDTO> getAllVisibleFilesInFolder(@PathVariable Long folderId) {
        return fileService.obtainAllVisibleFilesInFolder(folderId);
    }

    @PostMapping(value = "upload/{folderId}", consumes = "multipart/form-data", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public FileInfoDTO uploadNewFile(@PathVariable Long folderId,
                                     @NotNull(message = "'File' param is required and has to contain some file in it.") MultipartFile file) {
        try {
            return fileService.uploadFile(folderId, file);
        } catch (EmptyFileException | EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOOperationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("update/{fileId}")
    @ResponseStatus(HttpStatus.OK)
    public FileInfoDTO updateFile(@PathVariable Long fileId,
                           @NotNull(message = "'File' param is required and has to contain some file in it.") MultipartFile file) {
        try {
            return fileService.updateFile(fileId, file);
        } catch (EntityNotFoundException | EmptyFileException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOOperationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("visibility/{fileId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateFileVisibility(@PathVariable Long fileId, @RequestBody VisionLevelDTO visionLevel) {
        try {
            fileService.updateFileVisibility(fileId, visionLevel.visible());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("{fileId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFileById(@PathVariable Long fileId) {
        try {
            fileService.deleteFile(fileId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOOperationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
