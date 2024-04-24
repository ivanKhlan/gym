package ua.vixdev.gym.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.vixdev.gym.application.controller.ApplicationController;
import ua.vixdev.gym.application.controller.dto.ApplicationDto;
import ua.vixdev.gym.application.service.ApplicationService;

/**
 * Unit tests for {@link ApplicationController} class.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {

  private static final String END_POINT_PATH = "/applications";

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ApplicationService applicationService;

  private int testApplicationId;
  private ApplicationDto testApplicationDto;

  @BeforeEach
  public void init() {
    testApplicationId = 123;

    testApplicationDto = ApplicationDto.builder()
        .id(testApplicationId)
        .build();
  }

  @Test
  public void testGetAllApplications_return200Ok() throws Exception {
    List<ApplicationDto> expectedApplications = List.of(testApplicationDto, testApplicationDto);

    when(applicationService.getAllApplications()).thenReturn(expectedApplications);

    mockMvc.perform(get(END_POINT_PATH))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].id").value(testApplicationId))
        .andDo(print());
  }

  @Test
  public void testCreateApplication_shouldReturn_200Ok() throws Exception {
    when(applicationService.createApplication(any())).thenReturn(testApplicationDto);

    mockMvc.perform(post(END_POINT_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testApplicationDto)))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  public void testGetApplicationById() throws Exception {
    when(applicationService.getApplicationById(anyInt())).thenReturn(testApplicationDto);

    mockMvc.perform(get(END_POINT_PATH + "/" + testApplicationId))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  public void testUpdateApplication() throws Exception {
    when(applicationService.updateApplication(any())).thenReturn(testApplicationDto);

    mockMvc.perform(put(END_POINT_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(ApplicationDto.builder().build())))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  public void testDeleteApplication() throws Exception {
    when(applicationService.deleteApplication(anyInt())).thenReturn(testApplicationDto);

    mockMvc.perform(delete(END_POINT_PATH + "/" + anyInt()))
        .andExpect(status().isOk())
        .andDo(print());
  }
}
