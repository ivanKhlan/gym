package ua.vixdev.gym.reset_password.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.vixdev.gym.reset_password.dto.NewPasswordRequest;
import ua.vixdev.gym.reset_password.dto.ResetPasswordRequest;
import ua.vixdev.gym.reset_password.services.ResetPasswordService;

import static org.mockito.Mockito.*;

@WebMvcTest(controllers = ResetPasswordController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ResetPasswordControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private ResetPasswordService resetPasswordService;
    @InjectMocks
    private ResetPasswordController resetPasswordController;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(resetPasswordController).build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail_ValidRequest_EmailSent() {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("example@example.com");

        resetPasswordController.sendEmail(resetPasswordRequest);

        verify(resetPasswordService, times(1)).sendEmail(resetPasswordRequest);
    }

    @Test
    void resetPassword_ValidRequest_PasswordReset() {
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest("example@example.com", "newPassword123");

        resetPasswordController.resetPassword(newPasswordRequest);

        verify(resetPasswordService, times(1)).resetPassword(newPasswordRequest);
    }

    @Test
    void reviewToken_ValidToken_ReturnsTrue() {
        String token = "validToken";
        when(resetPasswordService.isTokenValid(token)).thenReturn(true);

        assertTrue(resetPasswordController.reviewToken(token));
    }

    @Test
    void reviewToken_InvalidToken_ReturnsFalse() {
        String token = "invalidToken";
        when(resetPasswordService.isTokenValid(token)).thenReturn(false);

        assertFalse(resetPasswordController.reviewToken(token));
    }
}
