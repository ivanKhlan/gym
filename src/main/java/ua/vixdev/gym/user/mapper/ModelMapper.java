package ua.vixdev.gym.user.mapper;

import ua.vixdev.gym.user.entity.UserEntity;

public interface ModelMapper {
    UserEntity convertDtoToUserEntity();
}
