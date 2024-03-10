package ua.vixdev.gym.service;

import ua.vixdev.gym.dto.CreateFolderDTO;
import ua.vixdev.gym.dto.FolderDto;
import ua.vixdev.gym.exceptions.FolderAlreadyExistsException;
import ua.vixdev.gym.exceptions.FolderNotEmptyException;
import ua.vixdev.gym.exceptions.EntityNotFoundException;
import ua.vixdev.gym.exceptions.IOOperationException;

import java.util.List;

public interface FolderTypesService {

    FolderDto obtainCertainFolder(Long folderId) throws EntityNotFoundException;

    List<FolderDto> getAllVisibleFolders();

    Long createNewFolder(CreateFolderDTO folder) throws FolderAlreadyExistsException;

    void changeFolderVisibility(Long folderId, Boolean visibilityLevel) throws EntityNotFoundException;

    void deleteFolder(Long folderId) throws EntityNotFoundException, FolderNotEmptyException, IOOperationException;
}
