package ua.vixdev.gym.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.dto.CreateFolderDTO;
import ua.vixdev.gym.dto.FolderDto;
import ua.vixdev.gym.entity.FolderTypes;
import ua.vixdev.gym.exceptions.*;
import ua.vixdev.gym.mapper.FolderTypesMapper;
import ua.vixdev.gym.repository.FolderTypesRepository;
import ua.vixdev.gym.service.FolderTypesService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FolderTypesServiceImpl implements FolderTypesService {

    private final FolderTypesMapper folderMapper;

    private final FolderTypesRepository folderRepository;

    @Override
    public FolderDto obtainCertainFolder(Long folderId) throws EntityNotFoundException {
        return folderMapper.mapEntityToDto(
                folderRepository.findByIdAndVisibleIsTrue(folderId)
                        .orElseThrow(() -> new EntityNotFoundException("Failed to obtain information about folder with id %s".formatted(folderId)))
        );
    }

    @Override
    public List<FolderDto> getAllVisibleFolders() {
        return folderRepository.getAllByVisibleIsTrue()
                .stream()
                .map(folderMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public Long createNewFolder(CreateFolderDTO folder) throws FolderAlreadyExistsException {
        File theDir = new File(folder.getTitle());
        if (!theDir.mkdir()) {
            log.info("Failed to save folder with name {}", folder.getTitle());
            throw new FolderAlreadyExistsException("Folder with the name %s is already exists.".formatted(folder.getTitle()));
        }

        if (!folder.getTitle().endsWith("/")) {
            log.info("Adding forward slash to the new directory: {}", folder.getTitle());
            folder.setTitle(folder.getTitle() + "/");
        }

        log.info("New folder {} successfully created", folder.getTitle());
        return folderRepository.save(folderMapper.mapDtoToEntity(folder)).getId();
    }

    @Override
    public void renameFolder(Long folderId, String newFolderName) throws EntityNotFoundException, FailedRenameFolderException {
        FolderTypes folder = obtainFolderByIdAndThrowExceptionIfNotFound(folderId);

        newFolderName += "\\";
        log.info("Added back slash to the directory name in renaming process: {}", newFolderName);

        File file = new File(folder.getTitle());
        newFolderName = file.getParentFile() + File.separator + newFolderName;
        log.info("New folder path will be {}", newFolderName);
        if (!file.renameTo(new File(newFolderName))) {
            log.warn("Failed to rename folder to {} from {}", newFolderName, folder.getTitle());
            throw new FailedRenameFolderException("Failed to rename folder from %s to %s. Most likely such a folder already exists.".formatted(folder.getTitle(), newFolderName));
        }
        folder.setTitle(newFolderName);
        folderRepository.save(folder);
        log.info("Folder was successfully renamed to {}", newFolderName);
    }

    @Override
    public void changeFolderVisibility(Long folderId, Boolean visibilityLevel) throws EntityNotFoundException {
        FolderTypes folder = obtainFolderByIdAndThrowExceptionIfNotFound(folderId);

        folder.setVisible(visibilityLevel);
        folder.setUpdatedAt(LocalDateTime.now());
        folderRepository.save(folder);
        log.info("Set visible level to {} for the folder with id {}", visibilityLevel, folderId);
    }

    @Override
    public void deleteFolder(Long folderId) throws EntityNotFoundException, FolderNotEmptyException, IOOperationException {
        FolderTypes folder = obtainFolderByIdAndThrowExceptionIfNotFound(folderId);

        if (!folder.getFiles().isEmpty())
            throw new FolderNotEmptyException("Failed to delete folder, because there are some files on it.");

        try {
            Files.delete(Path.of(folder.getTitle()));
        } catch (IOException e) {
            log.warn("Failed to delete folder (id {}). Reason is: {}", folderId, e.getCause());
            throw new IOOperationException("Failed to delete folder. Please try again later.");
        }

        log.info("Deleting folder with id {}", folderId);
        folderRepository.deleteById(folderId);
    }

    private FolderTypes obtainFolderByIdAndThrowExceptionIfNotFound(Long folderId) throws EntityNotFoundException {
        return folderRepository.findById(folderId)
                .orElseThrow(() -> new EntityNotFoundException("Failed to find folder with id %s".formatted(folderId)));
    }
}
