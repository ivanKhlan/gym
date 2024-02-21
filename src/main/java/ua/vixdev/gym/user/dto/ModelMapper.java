package ua.vixdev.gym.user.dto;

import ua.vixdev.gym.user.entity.UserEntity;

public interface ModelMapper {
    UserEntity convertDtoToUserEntity();
}
