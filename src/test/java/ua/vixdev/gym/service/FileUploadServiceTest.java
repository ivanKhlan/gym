package ua.vixdev.gym.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ua.vixdev.gym.dto.FileInfoDTO;
import ua.vixdev.gym.exceptions.EmptyFileException;
import ua.vixdev.gym.exceptions.FailedUploadImageException;
import ua.vixdev.gym.service.implementation.FileUploadServiceImpl;
import ua.vixdev.gym.validation.FileValidation;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileUploadServiceTest {

    @InjectMocks
    FileUploadServiceImpl uploadService;

    @Mock
    FileValidation validation;

    @Test
    void uploadImageToFileStorageTest_Successfully() throws Exception {
        MultipartFile file = new MultipartFileTest1("file", "randomFile.txt", MediaType.MULTIPART_FORM_DATA.toString(), "Some test".getBytes());
        doNothing().when(validation).validateFile(file);

        FileInfoDTO fileInfo = uploadService.uploadImageToFileStorage(file);

        assertEquals(file.getOriginalFilename(), fileInfo.fileName().split("_")[1]);
        assertEquals(file.getOriginalFilename(), fileInfo.fullFilePath().split("_")[1]);
        assertEquals(file.getContentType(), fileInfo.contentType());
        assertEquals(file.getSize() + "B", fileInfo.fileSize());

        verifyValidationMethodCall(file);
    }

    @Test
    void uploadImageToFileStorageTest_EmptyFile() throws Exception {
        MultipartFile file = new MockMultipartFile("name", new byte[]{});
        System.out.println("Second test: " + file);
        doThrow(new EmptyFileException("File is empty.")).when(validation).validateFile(file);

        Exception e = assertThrows(EmptyFileException.class, () -> uploadService.uploadImageToFileStorage(file));
        assertEquals(e.getMessage(), "File is empty.");

        verifyValidationMethodCall(file);
    }

    @Test
    void uploadImageToFileStorageTest_IOException() throws Exception {
        MultipartFileTest2 testFile =
                new MultipartFileTest2("file", "randomFile.txt", MediaType.MULTIPART_FORM_DATA.toString(), "Some test".getBytes());

        Exception e = assertThrows(FailedUploadImageException.class, () -> uploadService.uploadImageToFileStorage(testFile));
        assertEquals(e.getMessage(), "Something went wrong while uploading your file. Please try again later.");

        verifyValidationMethodCall(testFile);
    }

    private void verifyValidationMethodCall(MultipartFile file) throws EmptyFileException {
        verify(validation, times(1)).validateFile(file);
    }


    /**
     * Class required to simulate real file saving.
     * Otherwise, keep storing files in the project directory.
     */
    private static class MultipartFileTest1 extends MockMultipartFile {

        public MultipartFileTest1(String name, String originalFilename, String contentType, byte[] content) {
            super(name, originalFilename, contentType, content);
        }

        @Override
        public void transferTo(File dest) throws IllegalStateException {}
    }


    /**
     * Class required to simulate IOException thrown when calling transferTo() method.
     */
    private static class MultipartFileTest2 extends MockMultipartFile {

        public MultipartFileTest2(String name, String originalFilename, String contentType, byte[] content) {
            super(name, originalFilename, contentType, content);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            throw new IOException();
        }
    }
}
