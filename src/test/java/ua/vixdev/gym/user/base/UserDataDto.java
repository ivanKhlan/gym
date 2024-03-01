package ua.vixdev.gym.user.base;

import ua.vixdev.gym.user.controller.dto.CreateUserDto;

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
                "+380984643287",
                true
        );
    }

    public static CreateUserDto getSingleUserDtoWithFirstNameIgor() {
        return new CreateUserDto(
                "Igor",
                "Holovetskyi",
                "vholvetskyi@gmail.com",
                "passwo_rD-877",
                "+380984643278",
                true
        );
    }
}
