package ua.vixdev.gym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.vixdev.gym.dto.CreateFolderDTO;
import ua.vixdev.gym.dto.FolderDto;
import ua.vixdev.gym.dto.VisionLevelDTO;
import ua.vixdev.gym.exceptions.EntityNotFoundException;
import ua.vixdev.gym.exceptions.FolderAlreadyExistsException;
import ua.vixdev.gym.exceptions.FolderNotEmptyException;
import ua.vixdev.gym.exceptions.IOOperationException;
import ua.vixdev.gym.service.FolderTypesService;

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

    private final String COMMON_URL_PART = "/folders/";

    @BeforeEach
    void config() {
        folder = new FolderDto(1L, "C:/vixdev/", LocalDateTime.of(2024, 3, 9, 9, 10, 23), null);
    }

    @Test
    void getFolderInfoByItsId() throws Exception {
        when(folderService.obtainCertainFolder(1L)).thenReturn(folder);

        mockMvc.perform(get(COMMON_URL_PART + "1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(folder.getId()))
                .andExpect(jsonPath("$.title").value(folder.getTitle()))
                .andExpect(jsonPath("$.createdAt").value(folder.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(folder.getUpdatedAt()));
    }

    @Test
    void getFolderInfoById_EntityNotFoundException() throws Exception {
        String errMessage = "Failed to found folder with id 1.";

        when(folderService.obtainCertainFolder(1L)).thenThrow(new EntityNotFoundException(errMessage));

        mockMvc.perform(get(COMMON_URL_PART + "1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(errMessage));
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
                .andExpect(jsonPath("$.[0].updatedAt").value(folder.getUpdatedAt()));
    }

    @Test
    void createNewFolder() throws Exception {
        CreateFolderDTO folderToCreate = new CreateFolderDTO();
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
        CreateFolderDTO folderToCreate = new CreateFolderDTO();
        folderToCreate.setTitle(folder.getTitle());

        String errMessage = "Failed to create such folder. It already exists.";
        when(folderService.createNewFolder(folderToCreate)).thenThrow(new FolderAlreadyExistsException(errMessage));

        mockMvc.perform(post(COMMON_URL_PART)
                        .content(mapper.writeValueAsString(folderToCreate))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void updateFolderVisibility() throws Exception{
        VisionLevelDTO visionLevel = new VisionLevelDTO(true);
        doNothing().when(folderService).changeFolderVisibility(1L, visionLevel.visible());

        mockMvc.perform(put(COMMON_URL_PART + "visibility/1")
                        .content(mapper.writeValueAsString(visionLevel))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateFolderVisibility_EntityNotFoundException() throws Exception {
        VisionLevelDTO visionLevel = new VisionLevelDTO(true);
        String errMessage = "Folder with id 1 wasn't found.";

        doThrow(new EntityNotFoundException(errMessage)).when(folderService).changeFolderVisibility(1L, visionLevel.visible());

        mockMvc.perform(put(COMMON_URL_PART + "visibility/1")
                        .content(mapper.writeValueAsString(visionLevel))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void deleteFolderById() throws Exception {
        doNothing().when(folderService).deleteFolder(1L);

        mockMvc.perform(delete(COMMON_URL_PART + "1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteFolderById_EntityNotFoundException() throws Exception {
        String errMessage = "Failed to found folder with id 1.";

        doThrow(new EntityNotFoundException(errMessage)).when(folderService).deleteFolder(1L);

        mockMvc.perform(delete(COMMON_URL_PART + "1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void deleteFolderById_FolderNotEmptyException() throws Exception {
        String errMessage = "Folder with id 1 is not empty. Failed to delete";

        doThrow(new FolderNotEmptyException(errMessage)).when(folderService).deleteFolder(1L);

        mockMvc.perform(delete(COMMON_URL_PART + "1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(status().reason(errMessage));
    }

    @Test
    void deleteFolderById_IOOperationException() throws Exception {
        String errMessage = "Delete operation went wrong. Please try again later.";

        doThrow(new IOOperationException(errMessage)).when(folderService).deleteFolder(1L);

        mockMvc.perform(delete(COMMON_URL_PART + "1")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(status().reason(errMessage));
    }
}
