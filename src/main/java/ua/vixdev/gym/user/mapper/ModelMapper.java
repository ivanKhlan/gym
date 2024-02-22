package ua.vixdev.gym.user.mapper;

import ua.vixdev.gym.user.entity.UserEntity;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-21
 */
public interface ModelMapper {
    UserEntity convertDtoToUserEntity();
}
