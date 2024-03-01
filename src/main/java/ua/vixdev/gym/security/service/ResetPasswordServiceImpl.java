package ua.vixdev.gym.security.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.security.controller.dto.ChangePasswordDto;
import ua.vixdev.gym.security.exception.ChangePasswordException;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.service.UserService;

@Service
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void changePassword(Long id, ChangePasswordDto changePassword) {
        if (!StringUtils.equals(changePassword.getPassword(), changePassword.getRepeatPassword())) {
            throw new ChangePasswordException();
        }

        UserEntity user = userService.findUserEntityById(id);
        final var password = passwordEncoder.encode(changePassword.getPassword());
        user.changePassword(password);
    }
}
