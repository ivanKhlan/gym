package ua.vixdev.gym.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.dto.NewPasswordRequest;
import ua.vixdev.gym.dto.ResetPasswordRequest;
import ua.vixdev.gym.services.ResetPasswordService;

import java.util.Properties;


@RestController
@AllArgsConstructor
@RequestMapping("/api/reset-password")
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;
    @PostMapping
    public void sendEmail(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        resetPasswordService.sendEmail(resetPasswordRequest);
    }

    @PostMapping("/new-password")
    public void resetPassword(@RequestBody NewPasswordRequest newPasswordRequest){
        resetPasswordService.resetPassword(newPasswordRequest);
    }

    @ExceptionHandler()
    public ResponseEntity<?> handleException(){
        return null;
    }
}
