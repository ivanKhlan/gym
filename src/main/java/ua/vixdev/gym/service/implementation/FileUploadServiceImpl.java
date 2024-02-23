package ua.vixdev.gym.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.vixdev.gym.dto.FileInfoDTO;
import ua.vixdev.gym.exceptions.EmptyFileException;
import ua.vixdev.gym.exceptions.FailedUploadImageException;
import ua.vixdev.gym.service.FileUploadService;
import ua.vixdev.gym.validation.FileValidation;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Class with business logic of file storage (downloading to locale file system).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final FileValidation fileValidation;

    @Value("${file.path}")
    private String filePath;

    /**
     * Save file that was provided by user.
     * To each file name unique UUID is added. This is required to avoid collision in case two users upload images with the same name.
     *
     * @param file uploaded file itself.
     * @return brief details about stored file.
     * @throws FailedUploadImageException if file downloading process wasn't possible for some reasons.
     * @throws EmptyFileException in case user didn't mention any file in request body.
     */
    public FileInfoDTO uploadImageToFileStorage(MultipartFile file) throws FailedUploadImageException, EmptyFileException {
        fileValidation.validateFile(file);

        String newFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePathAndName = filePath + newFileName;

        try {
            file.transferTo(new File(filePathAndName));
            log.info("File successfully stored with the location: {}", filePathAndName);
        } catch (IOException e) {
            log.warn("Failed to upload file to {}.\nReason: {}", filePathAndName, e.getMessage());
            throw new FailedUploadImageException("Something went wrong while uploading your file. Please try again later.");
        }

        return new FileInfoDTO(newFileName, filePathAndName, file.getContentType(), file.getSize() + "B");
    }
}
