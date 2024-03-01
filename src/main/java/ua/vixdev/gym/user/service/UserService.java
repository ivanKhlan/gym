package ua.vixdev.gym.user.service;

import ua.vixdev.gym.user.controller.dto.CreateUserDto;
import ua.vixdev.gym.user.controller.dto.GetUserDetailsDto;
import ua.vixdev.gym.user.controller.dto.GetUserDto;
import ua.vixdev.gym.user.controller.dto.UpdateUserDto;
import ua.vixdev.gym.user.entity.UserEntity;

import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-22
 */
public interface UserService {

    List<GetUserDto> findUsersByFirstNameAndLastName(String firstName, String lastName);
    List<GetUserDto> findUsersByVisible(Boolean visible);
    List<GetUserDto> findUsersByFirstName(String firstName);
    List<GetUserDto> findUsersByLastName(String lastName);
    List<GetUserDto> findAllUsers();
    GetUserDetailsDto findUserById(Long id);
    UserEntity findUserEntityById(Long id);
    GetUserDto createNewUser(CreateUserDto createUserDto);
    GetUserDto updateUser(Long id, UpdateUserDto updateUserDto);
    void deleteUserById(Long id);
    void updateUserVisibility(Long id, String visible);
}
