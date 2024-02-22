package ua.vixdev.gym.user.service;

import ua.vixdev.gym.user.dto.UserDto;
import ua.vixdev.gym.user.entity.UserEntity;

import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-22
 */
public interface UserService {

    List<UserEntity> findUsersByFirstNameAndLastName(String firstName, String lastName);
    List<UserEntity> findUsersByVisible(Boolean visible);
    List<UserEntity> findUsersByFirstName(String firstName);
    List<UserEntity> findUsersByLastName(String lastName);
    List<UserEntity> findAllUsers();
    UserEntity findUserById(Long id);
    UserEntity createNewUser(UserDto userDto);
    UserEntity updateUser(Long id, UserDto userDto);
    void deleteUserById(Long id);
    void updateUserVisibility(Long id, String visible);
}
