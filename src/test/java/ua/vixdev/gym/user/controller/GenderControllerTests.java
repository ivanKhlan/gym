package ua.vixdev.gym.user.controller;

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
import ua.vixdev.gym.user.base.GenderDataDto;
import ua.vixdev.gym.user.base.UserDataDto;
import ua.vixdev.gym.user.dto.GenderDto;
import ua.vixdev.gym.user.dto.UserDto;
import ua.vixdev.gym.user.service.GenderService;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(GenderController.class)
class GenderControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GenderService genderService;


    @Test
    void create_gender_and_return_status_created() throws Exception {
        //given
        GenderDto gender = GenderDataDto.getSingleGenderDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/genders")
                        .content(toJsonString(gender))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void update_gender_and_return_status_accepted() throws Exception {
        //given
        GenderDto gender = GenderDataDto.getSingleGenderDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/users/1")
                        .content(toJsonString(gender))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }


    @Test
    void deleted_gender_and_return_status_no_content() throws Exception {
        //given
        GenderDto gender = GenderDataDto.getSingleGenderDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/users/1")
                        .content(toJsonString(gender))
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
