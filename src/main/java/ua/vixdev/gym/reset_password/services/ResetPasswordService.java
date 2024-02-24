package ua.vixdev.gym.reset_password.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.reset_password.dto.NewPasswordRequest;
import ua.vixdev.gym.reset_password.dto.ResetPasswordRequest;
import ua.vixdev.gym.reset_password.token.ResetPasswordToken;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor

public class ResetPasswordService {
    private final JavaMailSender emailSender;
    private final UserRepository userRepository;
    private ResetPasswordToken resetPasswordToken;
    public void sendEmail(ResetPasswordRequest resetPasswordRequest){
        Optional<UserEntity> user = userRepository.findByEmailAddress(resetPasswordRequest.getEmail());
        if (!user.isEmpty()){
            resetPasswordToken = generateToken(resetPasswordRequest.getEmail());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ivan.ssh07@gmail.com");
            message.setTo(resetPasswordRequest.getEmail());
            message.setSubject("Password Reset Request");

            message.setText("Your token: " + resetPasswordToken.getTokenBody() );

            emailSender.send(message);
        }else {
            throw new EntityNotFoundException("User with this email could not found ((");
        }
    }

    private ResetPasswordToken generateToken(String email) {
        UserEntity user = userRepository.findByEmailAddress(email).get();
        int code = user.hashCode();

        return null;
    }
    public boolean isTokenValid(String token){
//        return resetPasswordToken.isTokenCorrect(token) && !resetPasswordToken.isTokenExpired();
        return false;
    }
    public void resetPassword( NewPasswordRequest newPasswordRequest) {

    }

}
