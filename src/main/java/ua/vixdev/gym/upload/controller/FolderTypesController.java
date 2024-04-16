package ua.vixdev.gym.upload.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.upload.dto.CreateFolderDTO;
import ua.vixdev.gym.upload.dto.FolderDto;
import ua.vixdev.gym.upload.dto.RenameFolderDTO;
import ua.vixdev.gym.upload.dto.VisionLevelDTO;
import ua.vixdev.gym.upload.service.FolderTypesService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderTypesController {

    private final FolderTypesService folderService;

    @GetMapping("/{folderId}")
    public FolderDto getFolderById(@PathVariable Long folderId) {
        return folderService.findFolderById(folderId);
    }

    @GetMapping
    public List<FolderDto> getAllVisibleFolders() {
        return folderService.getAllVisibleFolders();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createNewFolder(@RequestBody @Valid CreateFolderDTO folder) {
        return folderService.createNewFolder(folder);
    }

    @PutMapping("/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public void renameFolder(@PathVariable Long folderId, @RequestBody @Valid RenameFolderDTO renamedFolder) {
        folderService.renameFolder(folderId, renamedFolder.newFolderName());
    }

    @PutMapping("/visibility/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateFolderVisibility(@PathVariable Long folderId, @RequestBody VisionLevelDTO visionLevel) {
        folderService.changeFolderVisibility(folderId, visionLevel.visible());
    }

    @DeleteMapping("/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
    }
}
