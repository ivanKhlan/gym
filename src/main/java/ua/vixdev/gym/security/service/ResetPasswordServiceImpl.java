package ua.vixdev.gym.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.security.controller.dto.ChangePasswordDto;
import ua.vixdev.gym.security.exception.ChangePasswordException;
import ua.vixdev.gym.user.service.UserService;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void changePassword(Long id, ChangePasswordDto changePassword) {
        log.debug("Entering in changePassword Method...");
        if (!StringUtils.equals(changePassword.getPassword(), changePassword.getRepeatPassword())) {
            log.warn("The passwords are not the same...");
            throw new ChangePasswordException();
        }

        final var user = userService.findUserById(id);
        final var password = passwordEncoder.encode(changePassword.getPassword());
        user.addPassword(password);
        log.info("Changing passwords for the User: {}", user.getEmail());
    }
}
