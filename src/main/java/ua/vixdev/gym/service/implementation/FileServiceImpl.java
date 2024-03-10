package ua.vixdev.gym.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.vixdev.gym.dto.FileInfoDTO;
import ua.vixdev.gym.dto.FileRepresentationDTO;
import ua.vixdev.gym.entity.Files;
import ua.vixdev.gym.exceptions.EmptyFileException;
import ua.vixdev.gym.exceptions.EntityNotFoundException;
import ua.vixdev.gym.exceptions.IOOperationException;
import ua.vixdev.gym.mapper.FileMapper;
import ua.vixdev.gym.repository.FilesRepository;
import ua.vixdev.gym.repository.FolderTypesRepository;
import ua.vixdev.gym.service.FileService;
import ua.vixdev.gym.utils.HashingUtil;
import ua.vixdev.gym.validation.FileValidation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FolderTypesRepository folderTypesRepository;
    private final FilesRepository filesRepository;

    private final FileValidation fileValidation;
    private final FileMapper fileMapper;

    @Override
    public FileRepresentationDTO obtainFileById(Long fileId) throws EntityNotFoundException, IOOperationException {
        log.info("Getting file via id {}", fileId);
        Files file = filesRepository.findByIdAndVisibleIsTrue(fileId)
                .orElseThrow(() -> new EntityNotFoundException("Failed to find file with id %s".formatted(fileId)));
        byte[] fileBytes;
        try {
            fileBytes = java.nio.file.Files.readAllBytes(new File(file.getFolder().getTitle() + file.getName()).toPath());
        } catch (IOException e) {
            log.warn("Failed to get info about file with id {}. Reason: {}", fileId, e.getCause());
            throw new IOOperationException("Failed to get image information. Please try again later.");
        }

        return new FileRepresentationDTO(fileId, file.getName(), file.getFolder().getTitle(), file.getCreatedAt(), file.getUpdatedAt(), Arrays.toString(fileBytes));
    }

    @Override
    public List<FileRepresentationDTO> obtainAllVisibleFilesInFolder(Long folderId) {
        return filesRepository.getAllByVisibleIsTrueAndFolderId(folderId)
                .stream()
                .map(file -> {
                    try {
                        return new FileRepresentationDTO(file.getId(), file.getName(), file.getFolder().getTitle(), file.getCreatedAt(), file.getUpdatedAt(),
                                Arrays.toString(java.nio.file.Files.readAllBytes(new File(file.getFolder().getTitle() + file.getName()).toPath())));
                    } catch (IOException e) {
                        log.warn("Failed to get info about file with id {}. Reason: {}", file.getId(), e.getCause());
                        throw new RuntimeException("Failed to get image information. Please try again later.");
                    }
                })
                .toList();
    }

    @Override
    public FileInfoDTO uploadFile(Long folderId, MultipartFile file) throws EmptyFileException, EntityNotFoundException, IOOperationException {
        fileValidation.validateFile(file);

        String[] splitArr = file.getOriginalFilename().split("\\.");
        String hashedFileName = HashingUtil.createHashedFileName() + "." + splitArr[splitArr.length - 1];
        log.info("New file name: {}. Length: {}", hashedFileName, hashedFileName.length());

        Files savedFile = filesRepository.save(fileMapper.mapMultipartFileToEntity(
                hashedFileName,
                folderTypesRepository.findById(folderId)
                        .orElseThrow(() -> new EntityNotFoundException("Folder with id %s does not exist.".formatted(folderId))))
        );
        String absPathToFile = savedFile.getFolder().getTitle() + savedFile.getName();

        try {
            file.transferTo(new File(absPathToFile));
            log.info("File successfully stored with the location: {}", absPathToFile);
        } catch (IOException e) {
            log.warn("Failed to upload file to {}.\nReason: {}", absPathToFile, e.getMessage());
            throw new IOOperationException("Something went wrong while uploading your file. Please try again later.");
        }

        return new FileInfoDTO(hashedFileName, absPathToFile, file.getContentType(), file.getSize() + "B");
    }

    @Override
    public FileInfoDTO updateFile(Long fileId, MultipartFile file) throws EntityNotFoundException, IOOperationException, EmptyFileException {
        Files fileToDelete = obtainFileAndThrowExceptionIfNotFound(fileId);
        FileInfoDTO fileInfo = uploadFile(fileToDelete.getFolder().getId(), file);
        deleteFile(fileId);
        return fileInfo;
    }

    @Override
    public void updateFileVisibility(Long fileId, Boolean visibility) throws EntityNotFoundException {
        Files file = obtainFileAndThrowExceptionIfNotFound(fileId);
        file.setVisible(visibility);
        file.setUpdatedAt(LocalDateTime.now());
        filesRepository.save(file);
        log.info("Updated file (id {}) visibility to {}", fileId, visibility);
    }

    @Override
    public void deleteFile(Long fileId) throws EntityNotFoundException, IOOperationException {
        Files file = obtainFileAndThrowExceptionIfNotFound(fileId);
        filesRepository.delete(file);

        try {
            java.nio.file.Files.delete(Path.of(file.getFolder().getTitle() + file.getName()));
        } catch (IOException e) {
            log.warn("Failed to delete file with id {}. Reason: {}", fileId, e.getCause());
            throw new IOOperationException("Failed to delete file. Please try again later.");
        }
        log.info("File with id {} was deleted", fileId);
    }

    private Files obtainFileAndThrowExceptionIfNotFound(Long fileId) throws EntityNotFoundException {
        return filesRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException(("Failed to find file with id %s".formatted(fileId))));
    }
}
