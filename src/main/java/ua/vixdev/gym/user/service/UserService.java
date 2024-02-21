package ua.vixdev.gym.user.service;

import ua.vixdev.gym.user.dto.CreateUserDto;
import ua.vixdev.gym.user.dto.UpdateUserDto;
import ua.vixdev.gym.user.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserEntity> findUsersByFirstNameAndLastName(String firstName, String lastName);
    List<UserEntity> findUsersByVisible(Boolean visible);
    List<UserEntity> findUsersByFirstName(String firstName);
    List<UserEntity> findUsersByLastName(String lastName);
    List<UserEntity> findAllUsers();
    UserEntity findUserById(Long id);
    UserEntity createNewUser(CreateUserDto createUserDto);
    UserEntity updateUser(Long id, UpdateUserDto updateUserDto);
    void deleteUserById(Long id);
    void updateUserVisibility(Long id, String visible);
}
