package ua.vixdev.gym.upload.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.vixdev.gym.upload.dto.CreateFolderDTO;
import ua.vixdev.gym.upload.dto.FolderDto;
import ua.vixdev.gym.upload.dto.RenameFolderDTO;
import ua.vixdev.gym.upload.dto.VisionLevelDTO;
import ua.vixdev.gym.upload.exceptions.*;
import ua.vixdev.gym.upload.service.FolderTypesService;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(FolderTypesController.class)
public class FolderTypesControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    FolderTypesService folderService;

    FolderDto folder;
    VisionLevelDTO visionLevelDTO;
    CreateFolderDTO folderToCreate;
    RenameFolderDTO renameFolderDTO;

    private final String COMMON_URL_PART = "/folders";

    @BeforeEach
    void config() {
        LocalDateTime creationTime = LocalDateTime.of(2024, 4, 10, 12, 55, 10);
        folder = new FolderDto(1L, "C:/vixdev/", creationTime, creationTime);
        visionLevelDTO = new VisionLevelDTO(true);
        folderToCreate = new CreateFolderDTO();
        renameFolderDTO = new RenameFolderDTO("some_file");
    }

    @Test
    void getFolderInfoByItsId() throws Exception {
        when(folderService.findFolderById(1L)).thenReturn(folder);

        mockMvc.perform(get(COMMON_URL_PART + "/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(folder.getId()))
                .andExpect(jsonPath("$.title").value(folder.getTitle()))
                .andExpect(jsonPath("$.createdAt").value(folder.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(folder.getUpdatedAt().toString()));
    }

    @Test
    void getFolderInfoById_EntityNotFoundException() throws Exception {
        when(folderService.findFolderById(1L)).thenThrow(new EntityNotFoundException(""));

        mockMvc.perform(get(COMMON_URL_PART + "/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllFolders() throws Exception {
        when(folderService.getAllVisibleFolders()).thenReturn(List.of(folder));

        mockMvc.perform(get(COMMON_URL_PART)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(folder.getId()))
                .andExpect(jsonPath("$.[0].title").value(folder.getTitle()))
                .andExpect(jsonPath("$.[0].createdAt").value(folder.getCreatedAt().toString()))
                .andExpect(jsonPath("$.[0].updatedAt").value(folder.getUpdatedAt().toString()));
    }

    @Test
    void createNewFolder() throws Exception {
        folderToCreate.setTitle(folder.getTitle());

        when(folderService.createNewFolder(folderToCreate)).thenReturn(1L);

        mockMvc.perform(post(COMMON_URL_PART)
                        .content(mapper.writeValueAsString(folderToCreate))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    void createNewFolder_FolderAlreadyExistsException() throws Exception {
        folderToCreate.setTitle(folder.getTitle());

        when(folderService.createNewFolder(folderToCreate)).thenThrow(new FolderAlreadyExistsException(""));

        mockMvc.perform(post(COMMON_URL_PART)
                        .content(mapper.writeValueAsString(folderToCreate))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void renameFolder() throws Exception {
        doNothing().when(folderService).renameFolder(1L, renameFolderDTO.newFolderName());

        mockMvc.perform(put(COMMON_URL_PART + "/1")
                .content(mapper.writeValueAsString(renameFolderDTO))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void renameFolder_EntityNotFoundException() throws Exception {
        doThrow(new EntityNotFoundException("")).when(folderService).renameFolder(1L, renameFolderDTO.newFolderName());

        mockMvc.perform(put(COMMON_URL_PART + "/1")
                .content(mapper.writeValueAsString(renameFolderDTO))
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void renameFolder_FailedRenameFolderException() throws Exception {
        doThrow(new FailedRenameFolderException("")).when(folderService).renameFolder(1L, renameFolderDTO.newFolderName());

        mockMvc.perform(put(COMMON_URL_PART + "/1")
                        .content(mapper.writeValueAsString(renameFolderDTO))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void updateFolderVisibility() throws Exception{
        doNothing().when(folderService).changeFolderVisibility(1L, visionLevelDTO.visible());

        mockMvc.perform(put(COMMON_URL_PART + "/visibility/1")
                        .content(mapper.writeValueAsString(visionLevelDTO))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateFolderVisibility_EntityNotFoundException() throws Exception {
        doThrow(new EntityNotFoundException("")).when(folderService).changeFolderVisibility(1L, visionLevelDTO.visible());

        mockMvc.perform(put(COMMON_URL_PART + "/visibility/1")
                        .content(mapper.writeValueAsString(visionLevelDTO))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFolderById() throws Exception {
        doNothing().when(folderService).deleteFolder(1L);

        mockMvc.perform(delete(COMMON_URL_PART + "/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteFolderById_EntityNotFoundException() throws Exception {
        doThrow(new EntityNotFoundException("")).when(folderService).deleteFolder(1L);

        mockMvc.perform(delete(COMMON_URL_PART + "/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFolderById_FolderNotEmptyException() throws Exception {
        doThrow(new FolderNotEmptyException("")).when(folderService).deleteFolder(1L);

        mockMvc.perform(delete(COMMON_URL_PART + "/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void deleteFolderById_IOOperationException() throws Exception {
        doThrow(new IOOperationException("")).when(folderService).deleteFolder(1L);

        mockMvc.perform(delete(COMMON_URL_PART + "/1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isServiceUnavailable());
    }
}
