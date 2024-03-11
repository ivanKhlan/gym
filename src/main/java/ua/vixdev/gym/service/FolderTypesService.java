package ua.vixdev.gym.service;

import ua.vixdev.gym.dto.CreateFolderDTO;
import ua.vixdev.gym.dto.FolderDto;
import ua.vixdev.gym.exceptions.*;

import java.util.List;

public interface FolderTypesService {

    FolderDto obtainCertainFolder(Long folderId) throws EntityNotFoundException;

    List<FolderDto> getAllVisibleFolders();

    Long createNewFolder(CreateFolderDTO folder) throws FolderAlreadyExistsException;

    void renameFolder(Long folderId, String newFolderName) throws EntityNotFoundException, FailedRenameFolderException;

    void changeFolderVisibility(Long folderId, Boolean visibilityLevel) throws EntityNotFoundException;

    void deleteFolder(Long folderId) throws EntityNotFoundException, FolderNotEmptyException, IOOperationException;
}
