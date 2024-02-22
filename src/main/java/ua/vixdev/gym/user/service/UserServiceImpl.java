package ua.vixdev.gym.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.user.dto.CreateUserDto;
import ua.vixdev.gym.user.dto.UpdateUserDto;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.exceptions.buisnes_logic.UserAlreadyExistsException;
import ua.vixdev.gym.user.exceptions.buisnes_logic.UserNotFoundException;
import ua.vixdev.gym.user.repository.UserEntityRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userRepository;

    @Override
    public List<UserEntity> findUsersByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<UserEntity> findUsersByVisible(Boolean visible) {
        return userRepository.findByVisible(visible);
    }

    @Override
    public List<UserEntity> findUsersByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    @Override
    public List<UserEntity> findUsersByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @Override
    public UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity createNewUser(CreateUserDto user) {
        checkIfEmailAlreadyExists(user.getEmail());
        var userEntity = user.convertDtoToUserEntity();
        return userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public UserEntity updateUser(Long id, UpdateUserDto userDto) {
        var loadUser = findUserById(id);
        if (loadUser.equalsEmail(userDto.getEmail())) {
            return loadUser.updateFields(userDto);
        }
        checkIfEmailAlreadyExists(userDto.getEmail());
        return loadUser.updateFields(userDto);
    }

    @Override
    public void deleteUserById(Long id) {
        findUserById(id);
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserVisibility(Long id, String visible) {
        var user = findUserById(id);
        user.changeUserVisibility(visible);
        userRepository.save(user);
    }

    private void checkIfEmailAlreadyExists(String email) {
        if (userRepository.findByEmailAddress(email).isPresent()) {
            throw new UserAlreadyExistsException(email);
        }
    }
}
