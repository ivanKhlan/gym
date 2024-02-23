package ua.vixdev.gym.service;

import org.springframework.web.multipart.MultipartFile;
import ua.vixdev.gym.dto.FileInfoDTO;
import ua.vixdev.gym.exceptions.EmptyFileException;
import ua.vixdev.gym.exceptions.FailedUploadImageException;

public interface FileUploadService {

    FileInfoDTO uploadImageToFileStorage(MultipartFile file) throws FailedUploadImageException, EmptyFileException;
}
