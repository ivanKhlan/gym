package ua.vixdev.gym.validation;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ua.vixdev.gym.exceptions.EmptyFileException;

@Component
public class FileValidation {

    /**
     * Checks whether file is not empty.
     * More validation criterias could be added (e.g. file types).
     *
     * @param file that user tries to upload
     */
    public void validateFile(MultipartFile file) throws EmptyFileException {
        if (file.isEmpty())
            throw new EmptyFileException("Uploaded file should not be empty");
    }
}