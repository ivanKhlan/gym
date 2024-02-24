package ua.vixdev.gym.reset_password.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.reset_password.dto.NewPasswordRequest;
import ua.vixdev.gym.reset_password.dto.ResetPasswordRequest;
import ua.vixdev.gym.reset_password.entity.ResetPasswordToken;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.repository.UserRepository;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor

public class ResetPasswordService {

    private final JavaMailSender emailSender;
    private final UserRepository userRepository;
    private final ResetTokenService resetTokenService;
    public void sendEmail(ResetPasswordRequest resetPasswordRequest){
        Optional<UserEntity> user = userRepository.findByEmailAddress(resetPasswordRequest.getEmail());
        if (!user.isEmpty()){
            ResetPasswordToken resetPasswordToken = resetTokenService.generateToken(resetPasswordRequest.getEmail());
            resetTokenService.saveToken(resetPasswordToken);
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


    public boolean isTokenValid(String token){
        return resetTokenService.isTokenValid(token);
    }
    public void resetPassword( NewPasswordRequest newPasswordRequest) {
        boolean isPasswordValid = isNewPasswordValid(newPasswordRequest.getNewPassword());
         if (isPasswordValid){
             UserEntity user = userRepository.findByEmailAddress(newPasswordRequest.getEmail()).orElseThrow(()->new EntityNotFoundException("User not found"));
             user.setPassword(newPasswordRequest.getNewPassword());
             userRepository.save(user);
         }else {
             throw new IllegalArgumentException("Password must be between 8 and 20 characters long and contain at least one letter and one number");
         }

    }
    private boolean isNewPasswordValid(String password){
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$"; //Довжина від 8 до 20 символів, яка містить принаймні одну літеру та одну цифру:

        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher matcher = passwordPattern.matcher(password);

        boolean isMatch = matcher.matches();
        return isMatch;
    }
}
