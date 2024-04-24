package ua.vixdev.gym.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.user.controller.dto.CreateUserDto;
import ua.vixdev.gym.user.controller.dto.GetUserDetailsDto;
import ua.vixdev.gym.user.controller.dto.GetUserDto;
import ua.vixdev.gym.user.controller.dto.UpdateUserDto;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.exceptions.UserAlreadyExistsException;
import ua.vixdev.gym.user.exceptions.UserNotFoundException;
import ua.vixdev.gym.user.repository.UserRepository;

import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-22
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<GetUserDto> findUsersByFirstNameAndLastName(String firstName, String lastName) {
        return printLogInfo(userRepository.findByFirstNameAndLastName(firstName, lastName));
    }

    @Override
    public List<GetUserDto> findUsersByVisible(Boolean visible) {
        return printLogInfo(userRepository.findByVisible(visible));
    }

    @Override
    public List<GetUserDto> findUsersByFirstName(String firstName) {
        return printLogInfo(userRepository.findByFirstName(firstName));
    }

    @Override
    public List<GetUserDto> findUsersByLastName(String lastName) {
        return printLogInfo(userRepository.findByLastName(lastName));
    }

    @Override
    public GetUserDetailsDto findUserById(Long id) {
        log.info("Loading user with ID: {}", id);
        var loadUser = userRepository.findById(id).orElseThrow(() -> {
            log.error("User with ID: {} not found!", id);
            return new UserNotFoundException(id);
        });

        return loadUser.toGetUserDetailsDto();
    }

    @Override
    public List<GetUserDto> findAllUsers() {
        return printLogInfo(userRepository.findAll());
    }

    @Transactional
    @Override
    public GetUserDto createNewUser(CreateUserDto createUserDto) {
        checkIfEmailAlreadyExists(createUserDto.getEmail());
        UserEntity savedUser = userRepository.save(new UserEntity(
                createUserDto.getFirstName(),
                createUserDto.getLastName(),
                createUserDto.getEmail(),
                passwordEncoder.encode(createUserDto.getPassword()),
                createUserDto.getPhoneNumber(),
                createUserDto.getVisible(),
                createUserDto.getRoles()));
        log.info("Saved user with ID: {}", savedUser.getId());
        return savedUser.toGetUserDto();
    }

    @Transactional
    @Override
    public GetUserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        final UserEntity updatedUser;
        final var loadUser = findUserEntityById(id);
        if (StringUtils.equals(loadUser.getEmail(), updateUserDto.getEmail())) {
            updatedUser = updateUserFields(loadUser, updateUserDto);
            return updatedUser.toGetUserDto();
        }
        checkIfEmailAlreadyExists(updateUserDto.getEmail());
        updatedUser = updateUserFields(loadUser, updateUserDto);
        return updatedUser.toGetUserDto();
    }

    @Override
    public void deleteUserById(Long id) {
        findUserById(id);
        userRepository.deleteById(id);
        log.info("Deleted user with ID: {}", id);
    }

    @Transactional
    @Override
    public void updateUserVisibility(Long id, String visible) {
        var user = findUserEntityById(id);
        user.changeUserVisibility(visible);
        log.info("Update user visibility for user with ID {} to: {}", id, visible);
    }

    private UserEntity updateUserFields(UserEntity loadUser, UpdateUserDto createUserDto) {
        var updatedUser = loadUser.updateFields(createUserDto);
        log.info("Updated user with ID: {}", updatedUser.getId());
        return updatedUser;
    }

    private void checkIfEmailAlreadyExists(String email) {
        if (userRepository.findByEmailAddress(email).isPresent()) {
            log.error("User with email: {} is already registered", email);
            throw new UserAlreadyExistsException(email);
        }
    }

    private List<GetUserDto> printLogInfo(List<UserEntity> users) {
        log.info("Size of loaded users from database: {}", users.size());
        return users.stream()
                .map(UserEntity::toGetUserDto)
                .toList();
    }

    public UserEntity findUserEntityById(Long id) {
        log.info("Loading user with ID: {}", id);
        return userRepository.findById(id).orElseThrow(() -> {
            log.error("User with ID: {} not found!", id);
            return new UserNotFoundException(id);
        });
    }
}
