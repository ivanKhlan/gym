package ua.vixdev.gym.reset_password.services;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.vixdev.gym.security.reset.entity.ResetPasswordToken;
import ua.vixdev.gym.security.reset.repository.ResetPasswordTokenRepository;
import ua.vixdev.gym.security.reset.services.ResetTokenService;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ResetTokenServiceTest {

    @Mock
    private ResetPasswordTokenRepository tokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ResetTokenService resetTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateToken_ValidEmail_TokenGenerated() {
        String email = "example@example.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail(email);
        when(userRepository.findByEmailAddress(email)).thenReturn(Optional.of(userEntity));

        ResetPasswordToken resetPasswordToken = resetTokenService.generateToken(email);

        assertNotNull(resetPasswordToken);
        assertEquals(String.valueOf(userEntity.hashCode()), resetPasswordToken.getTokenBody());
        assertEquals(userEntity, resetPasswordToken.getUser());
        assertTrue(LocalDateTime.now().isBefore(resetPasswordToken.getExpiryDate()));
    }

    @Test
    void saveToken_TokenSavedSuccessfully() {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();

        resetTokenService.saveToken(resetPasswordToken);

        verify(tokenRepository, times(1)).save(resetPasswordToken);
    }

    @Test
    void isTokenValid_ValidToken_ReturnsFalse() {
        String tokenValue = "validToken";
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setTokenBody(tokenValue);
        resetPasswordToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        when(tokenRepository.findByTokenBody(tokenValue)).thenReturn(Optional.of(resetPasswordToken));

        assertFalse(resetTokenService.isTokenValid(tokenValue));
    }

    @Test
    void isTokenValid_ExpiredToken_ReturnsTrue() {
        String tokenValue = "expiredToken";
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setTokenBody(tokenValue);
        resetPasswordToken.setExpiryDate(LocalDateTime.now().minusHours(1));
        when(tokenRepository.findByTokenBody(tokenValue)).thenReturn(Optional.of(resetPasswordToken));

        assertTrue(resetTokenService.isTokenValid(tokenValue));
    }

    @Test
    void isTokenValid_NonExistentToken_ThrowsException() {
        String tokenValue = "nonExistentToken";
        when(tokenRepository.findByTokenBody(tokenValue)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> resetTokenService.isTokenValid(tokenValue));
    }
}
