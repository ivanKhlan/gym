package ua.vixdev.gym.upload.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.vixdev.gym.upload.dto.FileInfoDTO;
import ua.vixdev.gym.upload.dto.FileRepresentationDTO;
import ua.vixdev.gym.upload.entity.FolderTypesEntity;
import ua.vixdev.gym.upload.entity.UploadFilesEntity;
import ua.vixdev.gym.upload.exceptions.EntityNotFoundException;
import ua.vixdev.gym.upload.exceptions.IOOperationException;
import ua.vixdev.gym.upload.mapper.FileMapper;
import ua.vixdev.gym.upload.repository.FilesRepository;
import ua.vixdev.gym.upload.repository.FolderTypesRepository;
import ua.vixdev.gym.upload.service.FileService;
import ua.vixdev.gym.upload.utils.HashingUtil;
import ua.vixdev.gym.upload.validation.FileValidation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
    public FileRepresentationDTO findFileById(Long fileId) {
        log.info("Getting file via id {}", fileId);
        UploadFilesEntity file = filesRepository.findByIdAndVisibleIsTrue(fileId)
                .orElseThrow(() -> new EntityNotFoundException("Failed to find file with id %s".formatted(fileId)));

        return mapEntityToDtoAndThrowExceptionIfFailed(file);
    }

    @Override
    public List<FileRepresentationDTO> findAllFilesInFolder(Long folderId) {
        FolderTypesEntity folder = folderTypesRepository.findById(folderId)
                .orElseThrow(() -> new EntityNotFoundException("Folder with id %s doesn't exist".formatted(folderId)));

        return folder.getFiles()
                .stream()
                .map(this::mapEntityToDtoAndThrowExceptionIfFailed)
                .toList();
    }

    @Override
    public FileInfoDTO uploadFile(Long folderId, MultipartFile file) {
        fileValidation.validateFile(file);


        String[] splitArr = file.getOriginalFilename().split("\\.");
        String hashedFileName = HashingUtil.createHashedFileName() + "." + splitArr[splitArr.length - 1];
        log.info("New file name: {}. Length: {}", hashedFileName, hashedFileName.length());

        UploadFilesEntity savedFile = filesRepository.save(fileMapper.mapMultipartFileToEntity(
                hashedFileName,
                folderTypesRepository.findById(folderId)
                        .orElseThrow(() -> new EntityNotFoundException("Folder with id %s does not exist.".formatted(folderId))))
        );

        try {
            file.transferTo(Paths.get(savedFile.getFullPath()));
            log.info("File successfully stored with the location: {}", savedFile.getFullPath());
        } catch (IOException e) {
            log.warn("Failed to upload file to {}.\nReason: {}", savedFile.getFullPath(), e.getMessage());
            throw new IOOperationException("Something went wrong while uploading your file. Please try again later.");
        }

        return new FileInfoDTO(savedFile.getId(), hashedFileName, savedFile.getFullPath(), file.getContentType(), file.getSize() + "B");
    }

    @Override
    @Transactional
    public FileInfoDTO updateFile(Long fileId, MultipartFile file) {
        log.info("Updating file with id {}", file);
        UploadFilesEntity fileToDelete = getFileAndThrowExceptionIfNotFound(fileId);
        FileInfoDTO fileInfo = uploadFile(fileToDelete.getFolder().getId(), file);

        UploadFilesEntity updatedFile = getFileAndThrowExceptionIfNotFound(fileInfo.fileId());
        updatedFile.setCreatedAt(fileToDelete.getCreatedAt());
        updatedFile.setUpdatedAt(LocalDateTime.now());
        filesRepository.save(updatedFile);

        deleteFile(fileId);
        return fileInfo;
    }

    @Override
    public void updateFileVisibility(Long fileId, Boolean visibility) {
        UploadFilesEntity file = getFileAndThrowExceptionIfNotFound(fileId);
        file.setVisible(visibility);
        file.setUpdatedAt(LocalDateTime.now());
        filesRepository.save(file);
        log.info("Updated file (id {}) visibility to {}", fileId, visibility);
    }

    @Override
    public void deleteFile(Long fileId) {
        UploadFilesEntity file = getFileAndThrowExceptionIfNotFound(fileId);
        filesRepository.delete(file);

        try {
            Files.delete(Path.of(file.getFullPath()));
        } catch (IOException e) {
            log.warn("Failed to delete file with id {}. Reason: {}", fileId, e.getCause());
            throw new IOOperationException("Failed to delete file. Please try again later.");
        }
        log.info("File with id {} was deleted", fileId);
    }

    private UploadFilesEntity getFileAndThrowExceptionIfNotFound(Long fileId) {
        return filesRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException(("Failed to find file with id %s".formatted(fileId))));
    }

    private FileRepresentationDTO mapEntityToDtoAndThrowExceptionIfFailed(UploadFilesEntity file) {
        try {
            return fileMapper.mapFileEntityToDTO(file);
        } catch (IOException e) {
            log.warn("Failed to get info about the file with id {}. Reason: {}", file.getId(), e.getCause());
            throw new IOOperationException("Failed to get image information. Please try again later.");
        }
    }
}
