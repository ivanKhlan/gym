package ua.vixdev.gym.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.security.controller.dto.ChangePasswordDto;
import ua.vixdev.gym.security.service.ResetPasswordService;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ResetPasswordController {
    private final ResetPasswordService resetPassword;

    /**
     * This method is used to change a user's password.
     *
     * @param changePassword This parameter represents the password to change.
     * @return Returns status 201(Accepted).
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/{id}/changePassword")
    public void changePassword(@PathVariable Long id,
                               @RequestBody @Valid ChangePasswordDto changePassword,
                               @AuthenticationPrincipal Long principalId) {
        if (!Objects.equals(id, principalId)) {
            log.warn("The authenticated User with ID: {}, does not have access to the resource", principalId);
            throw new AccessDeniedException("The authenticated User with ID: %d, does not have access to the resource".formatted(principalId));
        }
        resetPassword.changePassword(id, changePassword);
    }
}