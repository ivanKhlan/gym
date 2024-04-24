package ua.vixdev.gym.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.vixdev.gym.user.controller.dto.CreateUserDto;
import ua.vixdev.gym.user.controller.dto.GetUserDto;
import ua.vixdev.gym.user.controller.dto.UpdateUserDto;
import ua.vixdev.gym.user.data.UserDataTest;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.exceptions.UserNotFoundException;
import ua.vixdev.gym.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-22
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    //list user
    @Test
    void should_return_all_users() {
        //given
        List<UserEntity> users = UserDataTest.getListUserEntity();

        //when
        when(userRepository.findAll()).thenReturn(users);
        List<GetUserDto> usersDto = userService.findAllUsers();

        //then
        assertEquals(2, usersDto.size());
        assertEquals(usersDto.get(0).getEmail(), users.get(0).getEmail());
        verify(userRepository).findAll();
    }

    //detail user
    @Test
    void when_given_id_should_return_user_if_found(){
        //given
        UserEntity userEntity = UserDataTest.getUserEntityWithId();

        //when
        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        userService.deleteUserById(userEntity.getId());

        //then
        verify(userRepository).deleteById(userEntity.getId());
    }
    @Test()
    void should_throw_exception_when_user_doesnt_exist() {
        //given
        UserEntity userEntity = UserDataTest.getUserEntityWithId();

        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        UserNotFoundException userException = assertThrows(UserNotFoundException.class, () ->
                userService.deleteUserById(userEntity.getId()));

        //then
        assertTrue(userException.getMessage().contains("Could not find user with id {1}!"));
    }

    //create user
    @Test
    void when_save_user_should_return_user() {

        //given
        CreateUserDto createUserDto = UserDataTest.getCreateUserDto();
        UserEntity userEntity = UserDataTest.getUserEntity();

        //when
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        GetUserDto created = userService.createNewUser(createUserDto);

        //then
        assertThat(created.getFirstName()).isSameAs(createUserDto.getFirstName());
    }

    //update user
    @Test
    void when_given_id_should_update_username_if_found() {
        //given
        UserEntity user = UserDataTest.getUserEntityWithId();
        UpdateUserDto updatedUser = UserDataTest.getUpdateUserDto();

        //when
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        GetUserDto getUserDto = userService.updateUser(user.getId(), updatedUser);


        //then
        assertEquals("holvetsky@gmail.com", getUserDto.getEmail());
        verify(userRepository).findById(user.getId());
    }

    @Test()
    void should_throw_exception_when_update_user_doesnt_exist() {
        //given
        UserEntity userEntity = UserDataTest.getUserEntityWithId();

        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        UserNotFoundException userException = assertThrows(UserNotFoundException.class, () ->
                userService.deleteUserById(userEntity.getId()));

        //then
        assertTrue(userException.getMessage().contains("Could not find user with id {1}!"));
    }

    //delete user
    @Test
    void when_given_id_should_delete_user_if_found(){
        //given
        UserEntity userEntity = UserDataTest.getUserEntityWithId();

        //when
        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        userService.deleteUserById(userEntity.getId());

        //then
        verify(userRepository).deleteById(userEntity.getId());
    }
    @Test()
    void should_throw_exception_when_delete_user_doesnt_exist() {
        //given
        UserEntity userEntity = UserDataTest.getUserEntityWithId();

        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        UserNotFoundException userException = assertThrows(UserNotFoundException.class, () ->
                userService.deleteUserById(userEntity.getId()));

        //then
        assertTrue(userException.getMessage().contains("Could not find user with id {1}!"));
    }
}
