package ua.vixdev.gym.user.base;

import ua.vixdev.gym.user.dto.CreateUserDto;
import ua.vixdev.gym.user.dto.UpdateUserDto;

public abstract class UserDataDto {


    public static CreateUserDto getEmptyCreateUserDto() {
        return new CreateUserDto();
    }
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
