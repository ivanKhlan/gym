package ua.vixdev.gym.security.reset.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.security.reset.controller.dto.NewPasswordRequest;
import ua.vixdev.gym.security.reset.controller.dto.ResetPasswordRequest;
import ua.vixdev.gym.security.reset.services.ResetPasswordEmailService;


@RestController
@AllArgsConstructor
public class ResetPasswordEmailController {
    private final ResetPasswordEmailService resetPasswordService;
    @PostMapping("/lostPassword")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@RequestBody ResetPasswordRequest resetPasswordRequest) {

        resetPasswordService.sendEmail(resetPasswordRequest);
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody NewPasswordRequest newPasswordRequest){
       return resetPasswordService.resetPassword(newPasswordRequest);
    }
    @PostMapping("resetPassword/{token}")
    public boolean reviewToken(@PathVariable("token") String token){
        return resetPasswordService.isTokenValid(token);
    }


}
