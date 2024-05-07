package ua.vixdev.gym.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.user.controller.dto.UserDto;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.entity.UserRoleEntity;
import ua.vixdev.gym.user.exceptions.UserAlreadyExistsException;
import ua.vixdev.gym.user.exceptions.UserNotFoundException;
import ua.vixdev.gym.user.repository.UserRepository;
import ua.vixdev.gym.user.repository.UserRoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-22
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public UserEntity createUser(UserDto userDto) {
        validateUserEmailExists(userDto);
        return userRepository.save(mapToUserEntity(userDto));
    }

    @Transactional
    public UserEntity updateUser(Long id, UserDto userDto) {
        return userRepository.findById(id)
                .map(loadUser -> {
                    validateUserEmailExists(loadUser, userDto);
                    checkPasswordSame(loadUser, userDto);
                    updateRoles(loadUser, userDto.getRoleIds());
                    loadUser.updateFields(userDto);
                    return userRepository.save(loadUser);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteUserById(Long id) {
        validateUserExists(id);
        userRepository.deleteById(id);
        log.info("Deleted user with ID: {}", id);
    }

    private void validateUserEmailExists(UserEntity loadUser, UserDto user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()
                && !loadUser.equalEmails(user.getEmail())) {
            throw new UserAlreadyExistsException(loadUser.getEmail());
        }
    }

    private void updateRoles(UserEntity user, Set<Long> roleIds) {
        user.addRoles(fetchAllRolesByIds(roleIds));
    }

    private UserEntity mapToUserEntity(UserDto userDto) {
        return UserEntity.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .phoneNumber(userDto.getPhoneNumber())
                .visible(userDto.getVisible())
                .roles(fetchAllRolesByIds(userDto.getRoleIds()))
                .build();
    }

    private Set<UserRoleEntity> fetchAllRolesByIds(Set<Long> roleIds) {
        return new HashSet<>(userRoleRepository.findAllById(roleIds));
    }

    private void validateUserEmailExists(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(userDto.getEmail());
        }
    }

    private void checkPasswordSame(UserEntity loadUser, UserDto user) {
        if (!loadUser.getPassword().equals(user.getPassword())) {
            loadUser.addPassword(passwordEncoder.encode(user.getPassword()));
        }
    }

    private void validateUserExists(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }
    }
}
