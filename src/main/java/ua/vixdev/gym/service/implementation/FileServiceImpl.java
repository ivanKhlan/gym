package ua.vixdev.gym.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
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
import ua.vixdev.gym.validation.FileValidation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Class with business logic of file storage (downloading to locale file system).
 */
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
                .orElseThrow(() -> new EntityNotFoundException("Failed to found file with id %s".formatted(fileId)));
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
//                        throw new IOOperationException("Failed to get image information. Please try again later.");
                    }
                })
                .toList();
    }

    @Override
    public FileInfoDTO uploadFile(Long folderId, MultipartFile file) throws EmptyFileException, EntityNotFoundException, IOOperationException {
        fileValidation.validateFile(file);

        String[] splitArr = file.getOriginalFilename().split("\\.");
        String hashedFileName = DigestUtils.md5Hex(LocalDateTime.now().toString()) + "." + splitArr[splitArr.length - 1];
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
                .orElseThrow(() -> new EntityNotFoundException(("Failed to found file with id %s".formatted(fileId))));
    }

    /**
     * Save file that was provided by user.
     * To each file name unique UUID is added. This is required to avoid collision in case two users upload images with the same name.
     *
     * @param file uploaded file itself.
     * @return brief details about stored file.
     * @throws IOOperationException if file downloading process wasn't possible for some reasons.
     * @throws EmptyFileException         in case user didn't mention any file in request body.
     */
//    public FileInfoDTO uploadImageToFileStorage(MultipartFile file) throws FailedUploadImageException, EmptyFileException {
//        fileValidation.validateFile(file);
//
//        String newFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        String filePathAndName = filePath + newFileName;
//
//        try {
//            file.transferTo(new File(filePathAndName));
//            log.info("File successfully stored with the location: {}", filePathAndName);
//        } catch (IOException e) {
//            log.warn("Failed to upload file to {}.\nReason: {}", filePathAndName, e.getMessage());
//            throw new FailedUploadImageException("Something went wrong while uploading your file. Please try again later.");
//        }
//
//        return new FileInfoDTO(newFileName, filePathAndName, file.getContentType(), file.getSize() + "B");
//    }
}
