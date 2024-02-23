package ua.vixdev.gym.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import ua.vixdev.gym.dto.FileInfoDTO;
import ua.vixdev.gym.exceptions.EmptyFileException;
import ua.vixdev.gym.exceptions.FailedUploadImageException;
import ua.vixdev.gym.service.FileUploadService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    FileUploadService fileUploadService;

    final String TESTED_END_POINT = "/upload";

    private MockMultipartFile file;
    private FileInfoDTO fileInfo;

    @BeforeEach
    void fileSetup() {
        file = new MockMultipartFile("file", "some_file.txt", MediaType.MULTIPART_FORM_DATA.toString(), "Some test".getBytes());
        fileInfo = new FileInfoDTO(file.getOriginalFilename(), "C:/" + file.getOriginalFilename(), MediaType.MULTIPART_FORM_DATA.toString(), "1024");
    }

    @Test
    void uploadUserFileTest_201Response() throws Exception {
        when(fileUploadService.uploadImageToFileStorage(file)).thenReturn(fileInfo);

        mockMvc.perform(multipart(TESTED_END_POINT)
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fileName").value(file.getOriginalFilename()))
                .andExpect(jsonPath("$.fullFilePath").value("C:/" + file.getOriginalFilename()))
                .andExpect(jsonPath("$.contentType").value(MediaType.MULTIPART_FORM_DATA.toString()))
                .andExpect(jsonPath("$.fileSize").value("1024"));
    }

    @Test
    void uploadUserFileTest_409Response() throws Exception {
        when(fileUploadService.uploadImageToFileStorage(file)).thenThrow(new FailedUploadImageException("Failed. Try again later."));

        mockMvc.perform(multipart(TESTED_END_POINT)
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(status().reason(containsString("Failed. Try again later.")));
    }

    @Test
    void uploadUserFileTest_400Response() throws Exception {
        when(fileUploadService.uploadImageToFileStorage(file)).thenThrow(new EmptyFileException("File could not be empty."));

        mockMvc.perform(multipart(TESTED_END_POINT)
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("File could not be empty.")));
    }
}