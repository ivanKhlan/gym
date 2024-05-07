package ua.vixdev.gym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.vixdev.gym.user.controller.dto.UserHistoryDto;
import ua.vixdev.gym.user.controller.UserHistoryController;
import ua.vixdev.gym.user.entity.UserHistoryEntity;
import ua.vixdev.gym.user.mapper.UserHistoryMapper;
import ua.vixdev.gym.user.service.UserHistoryService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserHistoryController.class)
class UserHistoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserHistoryService userHistoryService;

    @MockBean
    UserHistoryMapper userHistoryMapper;

    private static final String END_POINT = "/history_changes/";

    private UserHistoryEntity userHistoryEntity;

    @BeforeEach
    public void setUp() {
        userHistoryEntity = new UserHistoryEntity(1L, 1, "test", LocalDateTime.now());
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllHistoryChanges_ReturnsHistoryChangesList() throws Exception {
        List<UserHistoryEntity> userHistoryEntityList = List.of(userHistoryEntity, userHistoryEntity);

        when(userHistoryService.findAll()).thenReturn(userHistoryEntityList);

        this.mockMvc.perform(get(END_POINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userHistoryEntityList)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetHistoryChangeById_ReturnsHistoryChange() throws Exception {
        when(userHistoryService.findById(1L)).thenReturn(userHistoryEntity);

        this.mockMvc.perform(get(END_POINT + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userHistoryEntity)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testCreateHistoryChange_ReturnsCreatedHistoryChange() throws Exception {
        when(userHistoryService.createUserHistory(new UserHistoryDto(1, "test"))).thenReturn(userHistoryEntity);

        this.mockMvc.perform(post(END_POINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userHistoryEntity)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testUpdateHistoryChangeDto_ReturnsUpdatedHistoryChange() throws Exception {
        Long id = 1L;
        UserHistoryDto requestDto = new UserHistoryDto(1, "updatedText");
        UserHistoryEntity updatedEntity = new UserHistoryEntity(id, 1, "updatedText", LocalDateTime.now());

        when(userHistoryService.updateUserHistory(id, requestDto)).thenReturn(updatedEntity);

        mockMvc.perform(put(END_POINT + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testDeleteHistoryChangeById_ReturnsString() throws Exception {
        Long id = 1L;

        doNothing().when(userHistoryService).deleteById(id);

        this.mockMvc.perform(delete(END_POINT + id))
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("history change with id '%d' was deleted".formatted(id)))
                )
                .andReturn();
    }
}