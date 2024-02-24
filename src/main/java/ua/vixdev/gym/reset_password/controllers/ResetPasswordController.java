package ua.vixdev.gym.reset_password.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.reset_password.dto.NewPasswordRequest;
import ua.vixdev.gym.reset_password.dto.ResetPasswordRequest;
import ua.vixdev.gym.reset_password.services.ResetPasswordService;


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
    @PostMapping("/{token}")
    public boolean reviewToken(@PathVariable("token") String token){
        return resetPasswordService.isTokenValid(token);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleException(EntityNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
