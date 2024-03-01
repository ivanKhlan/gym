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
import ua.vixdev.gym.user.base.UserDataDto;
import ua.vixdev.gym.user.controller.dto.CreateUserDto;
import ua.vixdev.gym.user.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-22
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Test
    void find_all_users_by_first_name_and_last_name() throws Exception {
        //given
        CreateUserDto user = UserDataDto.getSingleUserDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/users")
                        .queryParam("firstName", "Volodymyr")
                        .queryParam("lastName", "Holovetskyi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void find_all_users_by_first_name() throws Exception {
        //given
        CreateUserDto user = UserDataDto.getSingleUserDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/users")
                        .queryParam("firstName", "Volodymyr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void find_all_users_by_last_name() throws Exception {
        //given
        CreateUserDto user = UserDataDto.getSingleUserDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/users")
                        .queryParam("lastName", "Holovetskyi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void find_all_users_by_visible() throws Exception {
        //given
        CreateUserDto user = UserDataDto.getSingleUserDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/users")
                        .queryParam("visible", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void find_all_users_by_invisible() throws Exception {
        //given
        CreateUserDto user = UserDataDto.getSingleUserDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/users")
                        .queryParam("visible", "false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void find_all_users_and_return_status_ok() throws Exception {
        //given
        CreateUserDto user = UserDataDto.getSingleUserDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void create_user_and_return_status_created() throws Exception {
        //given
        CreateUserDto user = UserDataDto.getSingleUserDto();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:8080/users")
                        .content(toJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void update_user_and_return_status_accepted() throws Exception {
        //given
        CreateUserDto user = UserDataDto.getSingleUserDtoWithFirstNameIgor();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/users/1")
                        .content(toJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void update_user_visibility_and_return_status_accepted() throws Exception {
        //given
        Map<String, String> body = new HashMap<>();
        body.put("visible", "true");

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("http://localhost:8080/users/1/visible")
                        .content(toJsonString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void when_user_enter_wrong_value_then_return_status_bad_request() throws Exception {
        //given
        Map<String, String> body = new HashMap<>();
        body.put("visible", "tre");

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("http://localhost:8080/users/1")
                        .content(toJsonString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleted_user_and_return_status_no_content() throws Exception {
        //given
        CreateUserDto user = UserDataDto.getSingleUserDtoWithFirstNameIgor();

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("http://localhost:8080/users/1")
                        .content(toJsonString(user))
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
