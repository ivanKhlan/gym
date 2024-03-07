package ua.vixdev.gym.service;

import org.springframework.web.multipart.MultipartFile;
import ua.vixdev.gym.dto.FileRepresentationDTO;
import ua.vixdev.gym.dto.FileInfoDTO;
import ua.vixdev.gym.exceptions.EmptyFileException;
import ua.vixdev.gym.exceptions.IOOperationException;
import ua.vixdev.gym.exceptions.EntityNotFoundException;

import java.util.List;

public interface FileService {

    FileRepresentationDTO obtainFileById(Long fileId) throws EntityNotFoundException, IOOperationException;

    List<FileRepresentationDTO> obtainAllVisibleFilesInFolder(Long folderId);

    FileInfoDTO uploadFile(Long folderId, MultipartFile file) throws EmptyFileException, EntityNotFoundException, IOOperationException;

    void updateFileVisibility(Long fileId, Boolean visibility) throws EntityNotFoundException;

    void deleteFile(Long fileId) throws EntityNotFoundException, IOOperationException;

    FileInfoDTO updateFile(Long fileId, MultipartFile file) throws EntityNotFoundException, IOOperationException, EmptyFileException;

//    FileInfoDTO uploadImageToFileStorage(MultipartFile file) throws FailedUploadImageException, EmptyFileException;
}
