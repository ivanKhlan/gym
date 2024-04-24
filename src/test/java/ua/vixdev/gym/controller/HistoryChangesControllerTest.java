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
import ua.vixdev.gym.dto.HistoryChangesRequestDto;
import ua.vixdev.gym.entity.HistoryChangesEntity;
import ua.vixdev.gym.mapper.HistoryChangesMapper;
import ua.vixdev.gym.service.HistoryChangesService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HistoryChangesController.class)
class HistoryChangesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    HistoryChangesService historyChangesService;

    @MockBean
    HistoryChangesMapper historyChangesMapper;

    private static final String END_POINT = "/history_changes/";

    private HistoryChangesEntity historyChangesEntity;

    @BeforeEach
    public void setUp() {
        historyChangesEntity = new HistoryChangesEntity(1L, 1, "test", LocalDateTime.now());
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllHistoryChanges_ReturnsHistoryChangesList() throws Exception {
        List<HistoryChangesEntity> historyChangesEntityList = List.of(historyChangesEntity, historyChangesEntity);

        when(historyChangesService.getAllHistoryChanges()).thenReturn(historyChangesEntityList);

        this.mockMvc.perform(get(END_POINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(historyChangesEntityList)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetHistoryChangeById_ReturnsHistoryChange() throws Exception {
        when(historyChangesService.getHistoryChangesEntityById(1L)).thenReturn(historyChangesEntity);

        this.mockMvc.perform(get(END_POINT + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(historyChangesEntity)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testCreateHistoryChange_ReturnsCreatedHistoryChange() throws Exception {
        when(historyChangesService.createHistoryChange(new HistoryChangesRequestDto(1, "test"))).thenReturn(historyChangesEntity);

        this.mockMvc.perform(post(END_POINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(historyChangesEntity)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testUpdateHistoryChangeDto_ReturnsUpdatedHistoryChange() throws Exception {
        Long id = 1L;
        HistoryChangesRequestDto requestDto = new HistoryChangesRequestDto(1, "updatedText");
        HistoryChangesEntity updatedEntity = new HistoryChangesEntity(id, 1, "updatedText", LocalDateTime.now());

        when(historyChangesService.updateHistoryChange(id, requestDto)).thenReturn(updatedEntity);

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

        doNothing().when(historyChangesService).deleteHistoryChangeById(id);

        this.mockMvc.perform(delete(END_POINT + id))
                .andExpectAll(
                        status().isOk(),
                        content().string(containsString("history change with id '%d' was deleted".formatted(id)))
                )
                .andReturn();
    }
}