package ua.vixdev.gym.reset_password.services;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ua.vixdev.gym.reset_password.dto.NewPasswordRequest;
import ua.vixdev.gym.reset_password.dto.ResetPasswordRequest;
import ua.vixdev.gym.reset_password.entity.ResetPasswordToken;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ResetPasswordServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResetTokenService resetTokenService;

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private ResetPasswordService resetPasswordService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void sendEmail_UserFound_TokenGeneratedAndEmailSent() {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("example@example.com");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("example@example.com");
        when(userRepository.findByEmailAddress("example@example.com")).thenReturn(Optional.of(userEntity));
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setTokenBody("testToken");
        when(resetTokenService.generateToken("example@example.com")).thenReturn(resetPasswordToken);

        resetPasswordService.sendEmail(resetPasswordRequest);

        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendEmail_UserNotFound_ExceptionThrown() {
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("example@example.com");
        when(userRepository.findByEmailAddress("example@example.com")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> resetPasswordService.sendEmail(resetPasswordRequest));
    }

    @Test
    void isTokenValid_ValidToken_ReturnsTrue() {
        when(resetTokenService.isTokenValid("validToken")).thenReturn(true);

        boolean result = resetPasswordService.isTokenValid("validToken");

        assertTrue(result);
    }

    @Test
    void isTokenValid_InvalidToken_ReturnsFalse() {
        when(resetTokenService.isTokenValid("invalidToken")).thenReturn(false);

        boolean result = resetPasswordService.isTokenValid("invalidToken");

        assertFalse(result);
    }

    @Test
    void resetPassword_ValidPassword_PasswordChanged() {
        // Arrange
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest( "AaBbCcDd1","example@example.com");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("example@example.com");
        when(userRepository.findByEmailAddress("example@example.com")).thenReturn(Optional.of(userEntity));

        // Act
        resetPasswordService.resetPassword(newPasswordRequest);

        // Assert
        assertEquals("AaBbCcDd1", userEntity.getPassword());
    }


    @Test
    void resetPassword_InvalidPassword_ExceptionThrown() {
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest("example@example.com", "short");
        when(userRepository.findByEmailAddress("example@example.com")).thenReturn(Optional.of(new UserEntity()));

        assertThrows(IllegalArgumentException.class, () -> resetPasswordService.resetPassword(newPasswordRequest));
    }
}


