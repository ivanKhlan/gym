package ua.vixdev.gym.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.user.dto.CreateUserDto;
import ua.vixdev.gym.user.dto.UpdateUserDto;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.exceptions.buisnes_logic.UserVisibleException;
import ua.vixdev.gym.user.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    List<UserEntity> getAllUsers(
            @RequestParam Optional<String> firstName,
            @RequestParam Optional<String> lastName,
            @RequestParam Optional<Boolean> visible) {

        if (firstName.isPresent() && lastName.isPresent()) {
            return userService.findUsersByFirstNameAndLastName(firstName.get(), lastName.get());
        } else if (firstName.isPresent()) {
            return userService.findUsersByFirstName(firstName.get());
        } else if (lastName.isPresent()) {
            return userService.findUsersByLastName(lastName.get());
        } else if (visible.isPresent()) {
            return userService.findUsersByVisible(visible.get());
        }
        return userService.findAllUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    UserEntity findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping()
    ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDto createUser) {
        var user = userService.createNewUser(createUser);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDto updateUserDto) {
        var user = userService.updateUser(id, updateUserDto);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}/visible")
    ResponseEntity<?> updateUserVisibility(@PathVariable Long id,
                                           @RequestBody Map<String, String> body) {

        var visible = body.get("visible");
        if (StringUtils.isNotBlank(visible)
                && (StringUtils.equalsIgnoreCase(visible, "true") ||
                StringUtils.equalsIgnoreCase(visible, "false"))) {
            visible = visible.toLowerCase();
            userService.updateUserVisibility(id, visible);
            return ResponseEntity.accepted().build();
        }
        log.error("Incorrect data was provided when update the user's visibility: {}!", visible);
        throw new UserVisibleException(visible);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
