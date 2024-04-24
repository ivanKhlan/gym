package ua.vixdev.gym.security.reset.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.security.reset.repository.ResetPasswordTokenRepository;
import ua.vixdev.gym.security.reset.entity.ResetPasswordToken;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ResetTokenService {
    private final ResetPasswordTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public ResetPasswordToken generateToken(String email) {
        UserEntity user = userRepository.findByEmailAddress(email).get();
        String tokenBody = String.valueOf(user.hashCode());
        LocalDateTime expireAt = LocalDateTime.now().plusHours(24);

        ResetPasswordToken resetPasswordToken = ResetPasswordToken.builder()
                .expiryDate(expireAt)
                .tokenBody(tokenBody)
                .user(user)
                .build();
        saveToken(resetPasswordToken);
        return resetPasswordToken;
    }
    public void saveToken(ResetPasswordToken token){
        tokenRepository.save(token);
    }
    public boolean isTokenValid(String tokenValue){
        ResetPasswordToken resetPasswordToken = tokenRepository.findByTokenBody(tokenValue).orElseThrow(()-> new EntityNotFoundException("entity is not found"));
        return LocalDateTime.now().isAfter(resetPasswordToken.getExpiryDate()) && resetPasswordToken.getTokenBody().equals(tokenValue);
    }
}
