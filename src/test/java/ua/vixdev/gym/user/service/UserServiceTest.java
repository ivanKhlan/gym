package ua.vixdev.gym.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.vixdev.gym.user.base.UserDataDto;
import ua.vixdev.gym.user.base.UserEntityData;
import ua.vixdev.gym.user.dto.CreateUserDto;
import ua.vixdev.gym.user.dto.UpdateUserDto;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.exceptions.buisnes_logic.UserNotFoundException;
import ua.vixdev.gym.user.repository.UserEntityRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserEntityRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    //list user
    @Test
    void should_return_all_users() {
        //given
        List<UserEntity> users = UserEntityData.getListUserEntity();

        //when
        when(userRepository.findAll()).thenReturn(users);
        List<UserEntity> expected = userService.findAllUsers();

        //then
        assertEquals(expected, users);
        verify(userRepository).findAll();
    }

    //detail user
    @Test
    void when_given_id_should_return_user_if_found(){
        //given
        UserEntity userEntity = UserEntityData.getSingleUserEntityWithIdOne();

        //when
        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        userService.deleteUserById(userEntity.getId());

        //then
        verify(userRepository).deleteById(userEntity.getId());
    }
    @Test()
    void should_throw_exception_when_user_doesnt_exist() {
        //given
        UserEntity userEntity = UserEntityData.getSingleUserEntityWithIdOne();

        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        UserNotFoundException userException = assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(userEntity.getId()));

        //then
        assertTrue(userException.getMessage().contains("Could not find user with id {1}!"));
    }

    //create user
    @Test
    void when_save_user_should_return_user() {

        //given
        CreateUserDto userDto = UserDataDto.getSingleUserDto();
        UserEntity userEntity = UserEntityData.getSingleUserEntity();

        //when
        when(userRepository.save(ArgumentMatchers.any(UserEntity.class))).thenReturn(userEntity);
        UserEntity created = userService.createNewUser(userDto);

        //then
        assertThat(created.getFirstName()).isSameAs(userDto.getFirstName());
        verify(userRepository).save(userEntity);
    }

    //update user
    @Test
    void when_given_id_should_update_user_if_found() {
        //given
        UserEntity user = UserEntityData.getSingleUserEntityWithIdOne();
        UpdateUserDto updateUser = UserDataDto.getSingleUpdateUserDtoWithFirstNameIgor();
        UserEntity userEntity = updateUser.convertDtoToUserEntity();
        userEntity.setId(1L);

        //when
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.updateUser(user.getId(), updateUser);

        //then
        verify(userRepository).save(userEntity);
        verify(userRepository).findById(user.getId());
    }

    @Test()
    void should_throw_exception_when_update_user_doesnt_exist() {
        //given
        UserEntity userEntity = UserEntityData.getSingleUserEntityWithIdOne();

        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        UserNotFoundException userException = assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(userEntity.getId()));

        //then
        assertTrue(userException.getMessage().contains("Could not find user with id {1}!"));
    }

    //delete user
    @Test
    void when_given_id_should_delete_user_if_found(){
        //given
        UserEntity userEntity = UserEntityData.getSingleUserEntityWithIdOne();

        //when
        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        userService.deleteUserById(userEntity.getId());

        //then
        verify(userRepository).deleteById(userEntity.getId());
    }
    @Test()
    void should_throw_exception_when_delete_user_doesnt_exist() {
        //given
        UserEntity userEntity = UserEntityData.getSingleUserEntityWithIdOne();

        //when
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        UserNotFoundException userException = assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(userEntity.getId()));

        //then
        assertTrue(userException.getMessage().contains("Could not find user with id {1}!"));
    }
}
