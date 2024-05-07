package ua.vixdev.gym.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.user.controller.dto.UserDto;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.service.UserService;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-21
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    /**
     * This method is used to view the details of a specific user.
     *
     * @param id This is a parameter for the search criteria by ID and is required.
     * @return User Returns the specified user by ID with status 200(OK).
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserEntity findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    /**
     * This method is used to create a new user.
     *
     * @param userDto This parameter represents a new user.
     * @return Returns a new user along with the user's location with status 201(CREATED).
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntity createUser(@RequestBody @Valid UserDto userDto) {
        return userService.createUser(userDto);
    }

    /**
     * This method is used to update a user.
     *
     * @param id      This is a parameter for the search criteria by ID.
     * @param userDto This parameter represents the updated user.
     * @return Returns the updated user with status 202(ACCEPTED).
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserEntity updateUser(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    /**
     * This method is used to delete a user.
     *
     * @param id This is a parameter for the search criteria by ID.
     * @return Returns a status of 204(NO_CONTENT).
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
