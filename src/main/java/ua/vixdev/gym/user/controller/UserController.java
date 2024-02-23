package ua.vixdev.gym.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.user.dto.UserDto;
import ua.vixdev.gym.user.entity.UserEntity;
import ua.vixdev.gym.user.exceptions.buisnes_logic.UserVisibleException;
import ua.vixdev.gym.user.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
     * This method is used to find a list of users.
     * @param firstName The first parameter for the search criteria by firstName and is optional.
     * @param lastName  The second parameter for the search criteria by lastName and is optional.
     * @param visible  The third parameter for the search criteria by visible and is optional.
     * @return List<User> Returns all users according to the criteria or without them with status 200(OK).
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    @Cacheable("users")
    public List<UserEntity> getAllUsers(
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

    /**
     * This method is used to view the details of a specific user.
     * @param id This is a parameter for the search criteria by ID and is required.
     * @return User Returns the specified user by ID with status 200(OK).
     */
    @ResponseStatus(HttpStatus.OK)
    @Cacheable("userDetail")
    @GetMapping("/{id}")
    public UserEntity findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    /**
     * This method is used to create a new user.
     * @param userDto This parameter represents a new user.
     * @return Returns a new user along with the user's location with status 201(CREATED).
     */

    @Caching(evict = {
            @CacheEvict(value="users", allEntries=true),
            @CacheEvict(value="userDetail", allEntries=true)})
    @PostMapping()
    ResponseEntity<?> createUser(@RequestBody @Valid UserDto userDto) {
        var user = userService.createNewUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * This method is used to update a user.
     * @param id This is a parameter for the search criteria by ID.
     * @param userDto This parameter represents the updated user.
     * @return Returns the updated user with status 202(ACCEPTED).
     */

    @Caching(evict = {
            @CacheEvict(value="users", allEntries=true),
            @CacheEvict(value="userDetail", allEntries=true)})
    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        var user = userService.updateUser(id, userDto);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    /**
     * This method is used to update the user's visibility.
     * @param id This is a parameter for the search criteria by ID.
     * @param body This parameter represents the updated user's visibility.
     * @return Returns the updated user with status 202(ACCEPTED).
     */

    @Caching(evict = {
            @CacheEvict(value="users", allEntries=true),
            @CacheEvict(value="userDetail", allEntries=true)})
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
        log.error("Unknown visibility value: {}!", visible);
        throw new UserVisibleException(visible);
    }

    /**
     * This method is used to delete a user.
     * @param id This is a parameter for the search criteria by ID.
     * @return Returns a status of 204(NO_CONTENT).
     */
    @Caching(evict = {
            @CacheEvict(value="users", allEntries=true),
            @CacheEvict(value="userDetail", allEntries=true)})
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * This method is used to clear cache.
     * @return Returns a status 200(OK).
     */
    @Caching(evict = {
            @CacheEvict(value="users", allEntries=true),
            @CacheEvict(value="userDetail", allEntries=true)})
    @GetMapping("/clearCache")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> clearUsersCache() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
