package ua.vixdev.gym.upload.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.vixdev.gym.upload.dto.FileRepresentationDTO;
import ua.vixdev.gym.upload.dto.FileInfoDTO;
import ua.vixdev.gym.upload.dto.VisionLevelDTO;
import ua.vixdev.gym.upload.service.FileService;

import java.util.List;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{fileId}")
    @ResponseStatus(HttpStatus.OK)
    public FileRepresentationDTO getFileById(@PathVariable Long fileId) {
        return fileService.findFileById(fileId);
    }

    @GetMapping("/all/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public List<FileRepresentationDTO> getAllVisibleFilesInFolder(@PathVariable Long folderId) {
        return fileService.findAllFilesInFolder(folderId);
    }

    @PostMapping(value = "/upload/{folderId}", consumes = "multipart/form-data", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public FileInfoDTO uploadNewFile(@PathVariable Long folderId,
                                     @NotNull(message = "'File' param is required and has to contain some file in it.") MultipartFile file) {
        return fileService.uploadFile(folderId, file);
    }

    @PutMapping("/update/{fileId}")
    @ResponseStatus(HttpStatus.OK)
    public FileInfoDTO updateFile(@PathVariable Long fileId,
                           @NotNull(message = "'File' param is required and has to contain some file in it.") MultipartFile file) {
        return fileService.updateFile(fileId, file);
    }

    @PutMapping("/visibility/{fileId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateFileVisibility(@PathVariable Long fileId, @RequestBody VisionLevelDTO visionLevel) {
        fileService.updateFileVisibility(fileId, visionLevel.visible());
    }

    @DeleteMapping("/{fileId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFileById(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
    }
}
