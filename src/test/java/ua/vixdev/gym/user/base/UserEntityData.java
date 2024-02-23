package ua.vixdev.gym.user.base;

import ua.vixdev.gym.user.entity.UserEntity;

import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-22
 */
public abstract class UserEntityData {

    public static UserEntity getSingleUserEntity() {
        return new UserEntity(
                "Volodymyr",
                "Holovetskyi",
                "vholvetskyi@gmail.com",
                "passW00r-D$4d",
                "+3809846432",
                true
        );
    }

    public static UserEntity getSingleUserEntityWithIdOne() {
        var user = new UserEntity(
                "Volodymyr",
                "Holovetskyi",
                "vholvetskyi@gmail.com",
                "pass",
                "+3809846432",
                true
        );
        user.setId(1L);
        return user;
    }

    public static List<UserEntity> getListUserEntity() {
        return List.of(new UserEntity(
                "Volodymyr",
                "Holovetskyi",
                "vholvetskyi@gmail.com",
                "pass",
                "+3809846432",
                true
        ));
    }

}
