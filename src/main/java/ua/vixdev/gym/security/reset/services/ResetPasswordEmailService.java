package ua.vixdev.gym.security.reset.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.security.reset.ObjectValidator;
import ua.vixdev.gym.security.reset.controller.dto.NewPasswordRequest;
import ua.vixdev.gym.security.reset.controller.dto.ResetPasswordRequest;
import ua.vixdev.gym.security.reset.entity.ResetPasswordToken;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.repository.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor

public class ResetPasswordEmailService {

    private ObjectValidator objectValidator;
    private final JavaMailSender emailSender;
    private final UserRepository userRepository;
    private final ResetTokenService resetTokenService;
    public void sendEmail(ResetPasswordRequest resetPasswordRequest){
        Optional<UserEntity> user = userRepository.findByEmail(resetPasswordRequest.getEmail());
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
    public ResponseEntity<?> resetPassword(NewPasswordRequest newPasswordRequest) {
        Set<String> violations = objectValidator.validateObject(newPasswordRequest);
         if (violations.isEmpty()){
            boolean isPasswordValid = isNewPasswordValid(newPasswordRequest.getNewPassword());
            if (isPasswordValid) {
                UserEntity user = userRepository.findByEmail(newPasswordRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException("User not found"));

                user.setPassword(newPasswordRequest.getNewPassword());

                userRepository.save(user);

                return ResponseEntity.ok("");
            }
         }

        return ResponseEntity.badRequest().body(String.join(" \n ", violations));

    }
    private boolean isNewPasswordValid(String password){
        String passwordRegex = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"; //Довжина від 8 символів, яка містить принаймні одну літеру та одну цифру:

        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher matcher = passwordPattern.matcher(password);

        boolean isMatch = matcher.matches();
        return isMatch;
    }
}
