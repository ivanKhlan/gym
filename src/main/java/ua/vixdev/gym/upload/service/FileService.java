package ua.vixdev.gym.upload.service;

import org.springframework.web.multipart.MultipartFile;
import ua.vixdev.gym.upload.dto.FileRepresentationDTO;
import ua.vixdev.gym.upload.dto.FileInfoDTO;

import java.util.List;

public interface FileService {

    FileRepresentationDTO findFileById(Long fileId);

    List<FileRepresentationDTO> findAllFilesInFolder(Long folderId);

    FileInfoDTO uploadFile(Long folderId, MultipartFile file);

    void updateFileVisibility(Long fileId, Boolean visibility);

    void deleteFile(Long fileId);

    FileInfoDTO updateFile(Long fileId, MultipartFile file);
}
