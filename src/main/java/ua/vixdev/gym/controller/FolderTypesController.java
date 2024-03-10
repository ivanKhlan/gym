package ua.vixdev.gym.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.vixdev.gym.dto.CreateFolderDTO;
import ua.vixdev.gym.dto.FolderDto;
import ua.vixdev.gym.dto.VisionLevelDTO;
import ua.vixdev.gym.exceptions.FolderAlreadyExistsException;
import ua.vixdev.gym.exceptions.FolderNotEmptyException;
import ua.vixdev.gym.exceptions.EntityNotFoundException;
import ua.vixdev.gym.exceptions.IOOperationException;
import ua.vixdev.gym.service.FolderTypesService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/folders/")
@RequiredArgsConstructor
public class FolderTypesController {

    private final FolderTypesService folderService;

    @GetMapping("{folderId}")
    public FolderDto obtainFolderById(@PathVariable Long folderId) {
        try {
            return folderService.obtainCertainFolder(folderId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public List<FolderDto> obtainAllVisibleFolders() {
        return folderService.getAllVisibleFolders();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createNewFolder(@RequestBody @Valid CreateFolderDTO folder) {
        try {
            return folderService.createNewFolder(folder);
        } catch (FolderAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("visibility/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateFolderVisibility(@PathVariable Long folderId, @RequestBody VisionLevelDTO visionLevel) {
        try {
            folderService.changeFolderVisibility(folderId, visionLevel.visible());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFolder(@PathVariable Long folderId) {
        try {
            folderService.deleteFolder(folderId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (FolderNotEmptyException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (IOOperationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
