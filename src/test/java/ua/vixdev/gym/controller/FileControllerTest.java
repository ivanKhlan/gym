package ua.vixdev.gym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.vixdev.gym.dto.FileInfoDTO;
import ua.vixdev.gym.dto.FileRepresentationDTO;
import ua.vixdev.gym.dto.VisionLevelDTO;
import ua.vixdev.gym.exceptions.EmptyFileException;
import ua.vixdev.gym.exceptions.EntityNotFoundException;
import ua.vixdev.gym.exceptions.IOOperationException;
import ua.vixdev.gym.service.FileService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FileController.class)
public class FileControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    FileService fileService;

    final String COMMON_URL_PART = "/files/";

    MockMultipartHttpServletRequestBuilder builder;

    FileRepresentationDTO fileRepresentation;
    FileInfoDTO fileInfo;
    VisionLevelDTO visionLevel;
    MockMultipartFile file;

    @BeforeEach
    void fileSetup() {
        fileRepresentation = new FileRepresentationDTO(1L, "76327222e44e956a73d71564c47fac56.jpg", "C:/vixdev/temp/",
                LocalDateTime.of(2024, 3, 9, 10, 20, 10), null, "[-1, -40, -1, -32, 0, 16, 74]");

        file = new MockMultipartFile("file", "start_display.jpg", MediaType.MULTIPART_FORM_DATA.toString(), "Some text".getBytes());
        fileInfo = new FileInfoDTO(file.getOriginalFilename(), "C:/vixdev/" + file.getOriginalFilename(), file.getContentType(), "150B");

        builder = MockMvcRequestBuilders.multipart(COMMON_URL_PART + "update/1");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        visionLevel = new VisionLevelDTO(true);
    }

    @Test
    void obtainFileInfoById() throws Exception {
        when(fileService.obtainFileById(1L)).thenReturn(fileRepresentation);

        mockMvc.perform(get(COMMON_URL_PART + 1)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(fileRepresentation.id()))
                .andExpect(jsonPath("$.fileName").value(fileRepresentation.fileName()))
                .andExpect(jsonPath("$.filePath").value(fileRepresentation.filePath()))
                .andExpect(jsonPath("$.createdAt").value(fileRepresentation.createdAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(fileRepresentation.updatedAt()))
                .andExpect(jsonPath("$.fileArr").value(fileRepresentation.fileArr()));
    }

    @Test
    void obtainFileInfoById_EntityNotFoundException() throws Exception {
        String errMessage = "Failed to obtain info about file with id 1";

        when(fileService.obtainFileById(1L)).thenThrow(new EntityNotFoundException(errMessage));

        mockMvc.perform(get(COMMON_URL_PART + 1)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void obtainFileInfoById_IOOperationException() throws Exception {
        String errMessage = "Failed to download file info. Please try again later";

        when(fileService.obtainFileById(1L)).thenThrow(new IOOperationException(errMessage));

        mockMvc.perform(get(COMMON_URL_PART + 1)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void obtainAllFilesInFolder() throws Exception {
        when(fileService.obtainAllVisibleFilesInFolder(1L)).thenReturn(List.of(fileRepresentation));

        mockMvc.perform(get(COMMON_URL_PART + "all/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(fileRepresentation.id()))
                .andExpect(jsonPath("$.[0].fileName").value(fileRepresentation.fileName()))
                .andExpect(jsonPath("$.[0].filePath").value(fileRepresentation.filePath()))
                .andExpect(jsonPath("$.[0].createdAt").value(fileRepresentation.createdAt().toString()))
                .andExpect(jsonPath("$.[0].updatedAt").value(fileRepresentation.updatedAt()))
                .andExpect(jsonPath("$.[0].fileArr").value(fileRepresentation.fileArr()));
    }

    @Test
    void uploadFile() throws Exception {
        when(fileService.uploadFile(1L, file)).thenReturn(fileInfo);

        mockMvc.perform(multipart(COMMON_URL_PART + "upload/1")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fileName").value(fileInfo.fileName()))
                .andExpect(jsonPath("$.absPath").value(fileInfo.absPath()))
                .andExpect(jsonPath("$.contentType").value(fileInfo.contentType()))
                .andExpect(jsonPath("$.fileSize").value(fileInfo.fileSize()));
    }

    @Test
    void uploadFile_EmptyFileException() throws Exception {
        String errMessage = "File content should not be empty.";

        when(fileService.uploadFile(1L, file)).thenThrow(new EmptyFileException(errMessage));

        mockMvc.perform(multipart(COMMON_URL_PART + "upload/1")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void uploadFile_EntityNotFoundException() throws Exception {
        String errMessage = "Folder with id 1 wasn't found.";

        when(fileService.uploadFile(1L, file)).thenThrow(new EntityNotFoundException(errMessage));

        mockMvc.perform(multipart(COMMON_URL_PART + "upload/1")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void uploadFile_IOOperationException() throws Exception {
        String errMessage = "Uploading file operation went wrong. Please, try again later.";

        when(fileService.uploadFile(1L, file)).thenThrow(new IOOperationException(errMessage));

        mockMvc.perform(multipart(COMMON_URL_PART + "upload/1")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void updateFile() throws Exception {
        when(fileService.updateFile(1L, file)).thenReturn(fileInfo);

        mockMvc.perform(builder
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value(fileInfo.fileName()))
                .andExpect(jsonPath("$.absPath").value(fileInfo.absPath()))
                .andExpect(jsonPath("$.contentType").value(fileInfo.contentType()))
                .andExpect(jsonPath("$.fileSize").value(fileInfo.fileSize()));
    }

    @Test
    void updateFile_EntityNotFoundException() throws Exception {
        String errMessage = "Failed to found file with id 1.";

        when(fileService.updateFile(1L, file)).thenThrow(new EntityNotFoundException(errMessage));

        mockMvc.perform(builder
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void updateFile_EmptyFileException() throws Exception {
        String errMessage = "Provided file shoud not be empty.";

        when(fileService.updateFile(1L, file)).thenThrow(new EmptyFileException(errMessage));

        mockMvc.perform(builder
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void updateFile_IOOperationException() throws Exception {
        String errMessage = "Failed to update file. Please try again later.";

        when(fileService.updateFile(1L, file)).thenThrow(new IOOperationException(errMessage));

        mockMvc.perform(builder
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void updateFileVisibility() throws Exception {
        doNothing().when(fileService).updateFileVisibility(1L, visionLevel.visible());

        mockMvc.perform(put(COMMON_URL_PART + "visibility/1")
                        .content(mapper.writeValueAsString(visionLevel))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateFileVisibility_EntityNotFoundException() throws Exception {
        String errMessage = "File with id 1 wasn't found.";

        doThrow(new EntityNotFoundException(errMessage)).when(fileService).updateFileVisibility(1L, visionLevel.visible());

        mockMvc.perform(put(COMMON_URL_PART + "visibility/1")
                        .content(mapper.writeValueAsString(visionLevel))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void deleteFileById() throws Exception {
        doNothing().when(fileService).deleteFile(1L);

        mockMvc.perform(delete(COMMON_URL_PART + 1)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteFileById_EntityNotFoundException() throws Exception {
        String errMessage = "File with id 1 wasn't found.";

        doThrow(new EntityNotFoundException(errMessage)).when(fileService).deleteFile(1L);

        mockMvc.perform(delete(COMMON_URL_PART + 1)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void deleteFileById_IOOperationException() throws Exception {
        String errMessage = "Failed to delete file with id 1.";

        doThrow(new IOOperationException(errMessage)).when(fileService).deleteFile(1L);

        mockMvc.perform(delete(COMMON_URL_PART + 1)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(status().reason(errMessage));
    }
}