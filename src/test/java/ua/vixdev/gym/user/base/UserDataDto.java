package ua.vixdev.gym.user.base;

import ua.vixdev.gym.user.dto.CreateUserDto;
import ua.vixdev.gym.user.dto.UpdateUserDto;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-22
 */
public abstract class UserDataDto {


    public static CreateUserDto getSingleUserDto() {
        return new CreateUserDto(
                "Volodymyr",
                "Holovetskyi",
                "vholvetskyi@gmail.com",
                "passW00r-D$4d",
                "+3809846432",
                true
        );
    }

    public static UpdateUserDto getSingleUpdateUserDtoWithFirstNameIgor() {
        return new UpdateUserDto(
                "Igor",
                "Holovetskyi",
                "vholvetskyi@gmail.com",
                "pass",
                "+3809846432",
                true
        );
    }
}
