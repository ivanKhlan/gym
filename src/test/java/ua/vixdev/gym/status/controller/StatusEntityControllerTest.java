package ua.vixdev.gym.status.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.vixdev.gym.status.dto.StatusDto;
import ua.vixdev.gym.status.entity.StatusDtoData;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StatusController.class)
public class StatusEntityControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatusService statusService;


    @Test
    void find_all_statuses_by_value() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/status/all")
                        .queryParam("value", "new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void find_all_statuses_by_visible() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/status/all")
                        .queryParam("visible", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void find_all_statuses_by_invisible() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/status/all")
                        .queryParam("visible", "false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void find_all_statuses_and_return_status_ok() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/status/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void create_status_and_return_status_created() throws Exception {
        //given
        StatusDto status = StatusDtoData.getSingleStatusDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/status")
                        .content(toJsonString(status))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void update_status_and_return_status_accepted() throws Exception {
        //given
       StatusDto statusDto = StatusDtoData.getUpdatedStatusDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/status/1")
                        .content(toJsonString(statusDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void update_status_visibility_and_return_status_accepted() throws Exception {
        //given
        Map<String, String> body = new HashMap<>();
        body.put("visible", "true");

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("http://localhost:8080/status/1/visible")
                        .content(toJsonString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void when_accepted_wrong_value_then_return_status_bad_request() throws Exception {
        //given
        Map<String, String> body = new HashMap<>();
        body.put("visible", "true");

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/status/1")
                        .content(toJsonString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleted_status_and_return_status_no_content() throws Exception {
        //given
        StatusDto statusDto = StatusDtoData.getUpdatedStatusDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/status/1")
                        .content(toJsonString(statusDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private static String toJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
