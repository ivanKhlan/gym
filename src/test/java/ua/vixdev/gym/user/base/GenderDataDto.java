package ua.vixdev.gym.user.base;

import ua.vixdev.gym.user.dto.GenderDto;

public abstract class GenderDataDto {
    public static GenderDto getSingleGenderDto() {
        return new GenderDto(
                "some title",
                true
        );
    }
}
