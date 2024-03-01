package ua.vixdev.gym.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.vixdev.gym.security.controller.dto.LoginUserDto;
import ua.vixdev.gym.security.controller.dto.RegisterUserDto;
import ua.vixdev.gym.security.service.AuthenticationService;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-24
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = {AuthorizationController.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationService authenticationService;

    @Test
    void login_user_and_return_status_ok() throws Exception {
        //given
        LoginUserDto loginUser = new LoginUserDto("admin@gmial.com", "v_Ol0Dy$");

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(loginUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void register_user_and_return_status_ok() throws Exception {
        //given
        RegisterUserDto registerUser = new RegisterUserDto(
                "Igor",
                "Melnyk",
                "imelnyk@gmail.com",
                "v_Ol0Dy$",
                "+380987654342"
        );

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(registerUser))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    private static String toJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
