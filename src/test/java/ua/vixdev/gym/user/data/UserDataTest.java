package ua.vixdev.gym.user.data;

import ua.vixdev.gym.security.model.UserRole;
import ua.vixdev.gym.user.controller.dto.CreateUserDto;
import ua.vixdev.gym.user.controller.dto.UpdateUserDto;
import ua.vixdev.gym.user.entity.UserEntity;

import java.util.List;
import java.util.Set;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-22
 */
public abstract class UserDataTest {


    public static CreateUserDto getCreateUserDto() {
        return new CreateUserDto(
                "Volodymyr",
                "Holovetskyi",
                "vholvetskyi@gmail.com",
                "passW00r-D$4d",
                "+380984643287",
                true,
                Set.of(UserRole.ROLE_ADMIN)
        );
    }

    public static UpdateUserDto getUpdateUserDto() {
        return new UpdateUserDto(
                "Igor",
                "Holovetskyi",
                "holvetsky@gmail.com",
                "+380984643287",
                true,
                Set.of(UserRole.ROLE_ADMIN));
    }
    public static UserEntity getUserEntity() {
        return new UserEntity(
                "Volodymyr",
                "Holovetskyi",
                "vholvetskyi@gmail.com",
                "passW00r-D$4d",
                "+3809846432",
                true,
                Set.of(UserRole.ROLE_ADMIN)
        );
    }

    public static UserEntity getUserEntityWithId() {
        var user = new UserEntity(
                "Volodymyr",
                "Holovetskyi",
                "vholvetskyi@gmail.com",
                "pass",
                "+3809846432",
                true,
                Set.of(UserRole.ROLE_ADMIN)
        );
        user.setId(1L);
        return user;
    }

    public static List<UserEntity> getListUserEntity() {
        return List.of(
                new UserEntity(
                        "Volodymyr",
                        "Holovetskyi",
                        "vholvetskyi@gmail.com",
                        "pass",
                        "+3809846432",
                        true,
                        Set.of(UserRole.ROLE_ADMIN)),
                new UserEntity(
                        "Volodymyr",
                        "Holovetskyi",
                        "vholvetskyi@gmail.com",
                        "pass",
                        "+3809846432",
                        true,
                        Set.of(UserRole.ROLE_ADMIN))
        );
    }
}
