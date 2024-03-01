package ua.vixdev.gym.security.model;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.user.controller.dto.GetUserDto;
import ua.vixdev.gym.user.entity.UserEntity;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-24
 */
@Component
public class UserSecurity {

    public boolean isOwnerOrAdmin(GetUserDto requestedUser, GetUserDto authorizedUser) {
        return isAdmin(authorizedUser) || isOwner(requestedUser, authorizedUser);
    }

    public boolean isOwner(GetUserDto requestedUser, GetUserDto authorizedUser) {
        return authorizedUser.getEmail().equalsIgnoreCase(requestedUser.getEmail());
    }

    public boolean isAdmin(GetUserDto authorizedUser) {
        return authorizedUser.getRoles().stream()
                .anyMatch(role -> role.name().equalsIgnoreCase("ROLE_ADMIN"));
    }
}
