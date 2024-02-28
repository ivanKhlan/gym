package ua.vixdev.gym.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.user.dto.UserDto;
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
    public List<UserEntity> findUsersByFirstNameAndLastName(String firstName, String lastName) {
        return printLogInfo(userRepository.findByFirstNameAndLastName(firstName, lastName));
    }

    @Override
    public List<UserEntity> findUsersByVisible(Boolean visible) {
        return printLogInfo(userRepository.findByVisible(visible));
    }

    @Override
    public List<UserEntity> findUsersByFirstName(String firstName) {
        return printLogInfo(userRepository.findByFirstName(firstName));
    }

    @Override
    public List<UserEntity> findUsersByLastName(String lastName) {
        return printLogInfo(userRepository.findByLastName(lastName));
    }

    @Override
    public UserEntity findUserById(Long id) {
        var loadUser = userRepository.findById(id);
        log.info("Loaded user with ID: {}", id);

        return loadUser.orElseThrow(() -> {
            log.error("User with ID: {} not found!", id);
            return new UserNotFoundException(id);
        });
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return printLogInfo(userRepository.findAll());
    }

    @Transactional
    @Override
    public UserEntity createNewUser(UserDto userDto) {
        checkIfEmailAlreadyExists(userDto.getEmail());
        UserEntity savedUser = userRepository.save(new UserEntity(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getPhoneNumber(),
                userDto.getVisible(),
                userDto.getRoles()));
        log.info("Saved user with ID: {}", savedUser.getId());
        return savedUser;
    }

    @Transactional
    @Override
    public UserEntity updateUser(Long id, UserDto userDto) {
        var loadUser = findUserById(id);
        if (loadUser.equalsEmail(userDto.getEmail())) {
            return updateUserFields(loadUser, userDto);
        }
        checkIfEmailAlreadyExists(userDto.getEmail());
        return updateUserFields(loadUser, userDto);
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
        var user = findUserById(id);
        user.changeUserVisibility(visible);
        log.info("Update user visibility for user with ID {} to: {}", id, visible);
    }

    private UserEntity updateUserFields(UserEntity loadUser, UserDto userDto) {
        var updatedUser = loadUser.updateFields(userDto);
        log.info("Updated user with ID: {}", updatedUser.getId());
        return updatedUser;
    }

    private void checkIfEmailAlreadyExists(String email) {
        if (userRepository.findByEmailAddress(email).isPresent()) {
            log.error("User with email: {} is already registered", email);
            throw new UserAlreadyExistsException(email);
        }
    }

    private List<UserEntity> printLogInfo(List<UserEntity> users) {
        log.info("Size of loaded users from database: {}", users.size());
        return users;
    }
}
