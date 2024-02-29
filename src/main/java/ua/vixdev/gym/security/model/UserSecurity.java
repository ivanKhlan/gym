package ua.vixdev.gym.security.model;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.user.entity.UserEntity;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-24
 */
@Component
public class UserSecurity {

    public boolean isOwnerOrAdmin(UserEntity requestedUser, UserEntity loggedUser) {
        return isAdmin(loggedUser) || isOwner(requestedUser, loggedUser);
    }

    private boolean isOwner(UserEntity requestedUser, UserEntity loggedUser) {
        return loggedUser.getEmail().equalsIgnoreCase(requestedUser.getEmail());
    }

    private boolean isAdmin(UserEntity loggedUser) {
        return loggedUser.getRoles().stream()
                .anyMatch(role -> role.name().equalsIgnoreCase("ROLE_ADMIN"));
    }
}
