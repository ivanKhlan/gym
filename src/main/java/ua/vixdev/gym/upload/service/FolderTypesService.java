package ua.vixdev.gym.upload.service;

import ua.vixdev.gym.upload.dto.CreateFolderDTO;
import ua.vixdev.gym.upload.dto.FolderDto;

import java.util.List;

public interface FolderTypesService {

    FolderDto findFolderById(Long folderId);

    List<FolderDto> getAllVisibleFolders();

    Long createNewFolder(CreateFolderDTO folder);

    void renameFolder(Long folderId, String newFolderName);

    void changeFolderVisibility(Long folderId, Boolean visibilityLevel);

    void deleteFolder(Long folderId);
}
