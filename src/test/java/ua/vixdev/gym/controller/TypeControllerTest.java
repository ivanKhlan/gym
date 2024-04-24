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
import ua.vixdev.gym.category.controller.TypeController;
import ua.vixdev.gym.category.controller.dto.RequestTypeDto;
import ua.vixdev.gym.category.entity.TypeEntity;
import ua.vixdev.gym.exception.TypeValueTooLong;
import ua.vixdev.gym.category.controller.dto.ResponseTypeDtoFactory;
import ua.vixdev.gym.category.service.TypeService;
import ua.vixdev.gym.commons.utils.TypeValidationHelper;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TypeController.class)
class TypeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TypeService typeService;

    @MockBean
    ResponseTypeDtoFactory responseTypeDtoFactory;

    @MockBean
    TypeValidationHelper typeValidationHelper;

    private TypeEntity typeEntity;

    private RequestTypeDto requestTypeDto;

    private Long typeId;

    private final String URL_PATH = "/type/";

    @BeforeEach
    void setUp() {
        this.typeId = 1L;
        this.typeEntity = new TypeEntity(typeId, "test", true, Instant.now(), Instant.now(), null);
        this.requestTypeDto = new RequestTypeDto("test", true, Instant.now(), Instant.now(), null);

        MockitoAnnotations.openMocks(this);
    }

    // get all available types
    @Test
    public void getAllTypes_Success() throws Exception {
        List<TypeEntity> typeEntities = List.of(typeEntity, typeEntity, typeEntity);

        when(typeService.getAllTypes()).thenReturn(typeEntities);

        mockMvc.perform(get(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(typeEntities))
                ).andExpect(status().isOk())
                .andReturn();
    }

    // get type by its id
    @Test
    public void getTypeById_Success() throws Exception {
        when(typeService.getTypeById(1L)).thenReturn(Optional.of(typeEntity));

        mockMvc.perform(get(URL_PATH + typeId + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(typeEntity))
                )
                .andExpect(status().isOk())
                .andReturn();
    }

    // create type by given data
    @Test
    public void createTypeByGivenData_Success() throws Exception {
        when(typeService.saveTypeEntity(any(TypeEntity.class))).thenReturn(typeEntity);

        mockMvc.perform(post(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestTypeDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("type with id: 1 was created"));
    }

    // gives exception due to too long value
    @Test
    public void createTypeByGivenData_ValueTooLong() throws Exception {
        String longDescription = "a".repeat(71);
        RequestTypeDto requestTypeDto = new RequestTypeDto(longDescription, true, Instant.now(), Instant.now(), null);

        doThrow(new TypeValueTooLong("length of type value is too long"))
                .when(typeValidationHelper).checkTypeValueLength(requestTypeDto);

        mockMvc.perform(post(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestTypeDto)))
                .andExpect(status().isBadRequest());
    }

    // updates type
    @Test
    public void alterTypeEntity() throws Exception {

        when(typeService.getTypeById(typeId)).thenReturn(Optional.of(typeEntity));
        when(typeService.saveTypeEntity(any(TypeEntity.class))).thenReturn(typeEntity);

        mockMvc.perform(put(URL_PATH + "{id}/", typeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestTypeDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("type entity with id: 1 was altered"));
    }

    // deletes type by its id
    @Test
    public void deleteTypeEntity() throws Exception {

        when(typeService.getTypeById(typeId)).thenReturn(Optional.of(typeEntity));

        doNothing().when(typeService).deleteTypeById(typeId);

        mockMvc.perform(delete(URL_PATH + "{id}/", typeId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("type with id: %d was deleted".formatted(typeId))));
    }
}