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
import ua.vixdev.gym.security.controller.dto.ChangePasswordDto;
import ua.vixdev.gym.security.service.ResetPasswordService;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-24
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = {ResetPasswordController.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class ResetPasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ResetPasswordService passwordService;

    @Test
    void change_password_and_return_status_accepted() throws Exception {
        //given
        var changePassword = new ChangePasswordDto("v_Ol0Dy$", "v_Ol0Dy$");

        mockMvc.perform(MockMvcRequestBuilders.patch("http://localhost:8080/1/changePassword")
                        .queryParam("principalId","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(changePassword))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    private static String toJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
