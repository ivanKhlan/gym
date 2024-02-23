package ua.vixdev.gym.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.dto.NewPasswordRequest;
import ua.vixdev.gym.dto.ResetPasswordRequest;
import ua.vixdev.gym.token.ResetPasswordToken;

import java.awt.font.NumericShaper;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@Service
@AllArgsConstructor

public class ResetPasswordService {
    private final JavaMailSender emailSender;

    private ResetPasswordToken resetPasswordToken;
    public void sendEmail(ResetPasswordRequest resetPasswordRequest){
        resetPasswordToken = generateToken(resetPasswordRequest.getEmail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ivan.ssh07@gmail.com"); // Ваша електронна адреса
        message.setTo(resetPasswordRequest.getEmail());
        message.setSubject("Password Reset Request");

        message.setText("Hello,\n\nYou have requested to reset your password. " +
                "Click the link below to reset it:\n\nhttps://example.com/reset-password?token=" + resetPasswordToken.getTokenBody()
                +"  \n\nIf you did not request this, please ignore this email. \n\n" + resetPasswordToken.getTokenBody()
                + "  token will expire after 10 minutes");

        emailSender.send(message);
    }

    private ResetPasswordToken generateToken(String email) {
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime deleteAt = createdAt.plusSeconds(60);

        Integer tokenBody = new Random().nextInt(100_000) + 1;
        return ResetPasswordToken.builder()
                .tokenBody(tokenBody)
                .createdAt(createdAt)
                .expiredAt(deleteAt)
                .email(email)
                .build();
    }

    public void resetPassword( NewPasswordRequest newPasswordRequest) {
        if (!resetPasswordToken.isTokenExpired() && newPasswordRequest.getToken().equals(resetPasswordToken.getTokenBody())){
            //todo: exception handler
            // todo:
        }
    }

}
