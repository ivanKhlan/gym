package ua.vixdev.gym.reset_password.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.vixdev.gym.security.reset.controller.dto.NewPasswordRequest;
import ua.vixdev.gym.security.reset.controller.dto.ResetPasswordRequest;
import ua.vixdev.gym.security.reset.services.ResetPasswordEmailService;
import ua.vixdev.gym.security.reset.controller.ResetPasswordEmailController;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ResetPasswordEmailController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ResetPasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ResetPasswordEmailService resetPasswordService;
    @InjectMocks
    private ResetPasswordEmailController resetPasswordController;
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail_ValidRequest_EmailSent() throws Exception {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("email32@gmail.com");
        String resetPasswordString = objectMapper.writeValueAsString(resetPasswordRequest);
        doNothing().when(resetPasswordService).sendEmail(resetPasswordRequest);
        mockMvc.perform(post("/api/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(resetPasswordString)
        ).andExpect(status().isOk());
        verify(resetPasswordService, times(1)).sendEmail(resetPasswordRequest);
    }

    @Test
    void resetPassword_ValidRequest_PasswordReset() throws Exception {
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest("1*)(_dqwdfqicNNdwqw", "example123@gmail.com");
        String jsonDto = objectMapper.writeValueAsString(newPasswordRequest);
        mockMvc.perform(post("/api/reset-password/new-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDto)
        ).andExpect(status().isOk());

        verify(resetPasswordService, times(1)).resetPassword(newPasswordRequest);
    }

    @Test
    void reviewToken_ValidToken_ReturnsTrue() throws Exception {
        String token = "validToken";
        when(resetPasswordService.isTokenValid(token)).thenReturn(true);
        mockMvc.perform(post("/api/reset-password/{validToken}", token))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

    }

    @Test
    void reviewToken_InvalidToken_ReturnsFalse() throws Exception {
        String token = "invalidToken";

        when(resetPasswordService.isTokenValid(token)).thenReturn(false);
        mockMvc.perform(post("/api/reset-password/{invalidToken}", token))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));


    }
}
