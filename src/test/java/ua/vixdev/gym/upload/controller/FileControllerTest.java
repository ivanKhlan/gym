package ua.vixdev.gym.upload.controller;

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
import ua.vixdev.gym.upload.dto.FileInfoDTO;
import ua.vixdev.gym.upload.dto.FileRepresentationDTO;
import ua.vixdev.gym.upload.dto.VisionLevelDTO;
import ua.vixdev.gym.upload.exceptions.EmptyFileException;
import ua.vixdev.gym.upload.exceptions.EntityNotFoundException;
import ua.vixdev.gym.upload.exceptions.IOOperationException;
import ua.vixdev.gym.upload.service.FileService;

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

    final String COMMON_URL_PART = "/files";

    MockMultipartHttpServletRequestBuilder builder;

    FileRepresentationDTO fileRepresentation;
    FileInfoDTO fileInfo;
    VisionLevelDTO visionLevel;
    MockMultipartFile file;

    @BeforeEach
    void fileSetup() {
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 4, 11, 15, 13, 25);
        fileRepresentation = new FileRepresentationDTO(1L, "76327222e44e956a73d71564c47fac56.jpg", "C:/vixdev/temp/",
                currentDateTime, currentDateTime, "[-1, -40, -1, -32, 0, 16, 74]");

        file = new MockMultipartFile("file", "start_display.jpg", MediaType.MULTIPART_FORM_DATA.toString(), "Some text".getBytes());
        fileInfo = new FileInfoDTO(1L, file.getOriginalFilename(), "C:/vixdev/" + file.getOriginalFilename(), file.getContentType(), "150B");

        builder = MockMvcRequestBuilders.multipart(COMMON_URL_PART + "/update/1");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        visionLevel = new VisionLevelDTO(true);
    }

    @Test
    void getFileInfoById() throws Exception {
        when(fileService.findFileById(1L)).thenReturn(fileRepresentation);

        mockMvc.perform(get(COMMON_URL_PART + "/1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(fileRepresentation.id()))
                .andExpect(jsonPath("$.fileName").value(fileRepresentation.fileName()))
                .andExpect(jsonPath("$.filePath").value(fileRepresentation.filePath()))
                .andExpect(jsonPath("$.createdAt").value(fileRepresentation.createdAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(fileRepresentation.updatedAt().toString()))
                .andExpect(jsonPath("$.byteRepresentation").value(fileRepresentation.byteRepresentation()));
    }

    @Test
    void getFileInfoById_EntityNotFoundException() throws Exception {
        when(fileService.findFileById(1L)).thenThrow(new EntityNotFoundException(""));

        mockMvc.perform(get(COMMON_URL_PART + "/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getFileInfoById_IOOperationException() throws Exception {
        when(fileService.findFileById(1L)).thenThrow(new IOOperationException(""));

        mockMvc.perform(get(COMMON_URL_PART + "/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    void getAllFilesInFolder() throws Exception {
        when(fileService.findAllFilesInFolder(1L)).thenReturn(List.of(fileRepresentation));

        mockMvc.perform(get(COMMON_URL_PART + "/all/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(fileRepresentation.id()))
                .andExpect(jsonPath("$.[0].fileName").value(fileRepresentation.fileName()))
                .andExpect(jsonPath("$.[0].filePath").value(fileRepresentation.filePath()))
                .andExpect(jsonPath("$.[0].createdAt").value(fileRepresentation.createdAt().toString()))
                .andExpect(jsonPath("$.[0].updatedAt").value(fileRepresentation.updatedAt().toString()))
                .andExpect(jsonPath("$.[0].byteRepresentation").value(fileRepresentation.byteRepresentation()));
    }

    @Test
    void uploadFile() throws Exception {
        when(fileService.uploadFile(1L, file)).thenReturn(fileInfo);

        mockMvc.perform(multipart(COMMON_URL_PART + "/upload/1")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fileId").value(fileInfo.fileId()))
                .andExpect(jsonPath("$.fileName").value(fileInfo.fileName()))
                .andExpect(jsonPath("$.absPath").value(fileInfo.absPath()))
                .andExpect(jsonPath("$.contentType").value(fileInfo.contentType()))
                .andExpect(jsonPath("$.fileSize").value(fileInfo.fileSize()));
    }

    @Test
    void uploadFile_EmptyFileException() throws Exception {
        when(fileService.uploadFile(1L, file)).thenThrow(new EmptyFileException(""));

        mockMvc.perform(multipart(COMMON_URL_PART + "/upload/1")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void uploadFile_EntityNotFoundException() throws Exception {
        when(fileService.uploadFile(1L, file)).thenThrow(new EntityNotFoundException(""));

        mockMvc.perform(multipart(COMMON_URL_PART + "/upload/1")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void uploadFile_IOOperationException() throws Exception {
        when(fileService.uploadFile(1L, file)).thenThrow(new IOOperationException(""));

        mockMvc.perform(multipart(COMMON_URL_PART + "/upload/1")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    void updateFile() throws Exception {
        when(fileService.updateFile(1L, file)).thenReturn(fileInfo);

        mockMvc.perform(builder
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileId").value(fileInfo.fileId()))
                .andExpect(jsonPath("$.fileName").value(fileInfo.fileName()))
                .andExpect(jsonPath("$.absPath").value(fileInfo.absPath()))
                .andExpect(jsonPath("$.contentType").value(fileInfo.contentType()))
                .andExpect(jsonPath("$.fileSize").value(fileInfo.fileSize()));
    }

    @Test
    void updateFile_EntityNotFoundException() throws Exception {
        when(fileService.updateFile(1L, file)).thenThrow(new EntityNotFoundException(""));

        mockMvc.perform(builder
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateFile_EmptyFileException() throws Exception {
        when(fileService.updateFile(1L, file)).thenThrow(new EmptyFileException(""));

        mockMvc.perform(builder
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateFile_IOOperationException() throws Exception {
        when(fileService.updateFile(1L, file)).thenThrow(new IOOperationException(""));

        mockMvc.perform(builder
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    void updateFileVisibility() throws Exception {
        doNothing().when(fileService).updateFileVisibility(1L, visionLevel.visible());

        mockMvc.perform(put(COMMON_URL_PART + "/visibility/1")
                        .content(mapper.writeValueAsString(visionLevel))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateFileVisibility_EntityNotFoundException() throws Exception {
        doThrow(new EntityNotFoundException("")).when(fileService).updateFileVisibility(1L, visionLevel.visible());

        mockMvc.perform(put(COMMON_URL_PART + "/visibility/1")
                        .content(mapper.writeValueAsString(visionLevel))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFileById() throws Exception {
        doNothing().when(fileService).deleteFile(1L);

        mockMvc.perform(delete(COMMON_URL_PART + "/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteFileById_EntityNotFoundException() throws Exception {
        doThrow(new EntityNotFoundException("")).when(fileService).deleteFile(1L);

        mockMvc.perform(delete(COMMON_URL_PART + "/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFileById_IOOperationException() throws Exception {
        doThrow(new IOOperationException("")).when(fileService).deleteFile(1L);

        mockMvc.perform(delete(COMMON_URL_PART + "/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isServiceUnavailable());
    }
}