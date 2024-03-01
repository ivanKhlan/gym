package ua.vixdev.gym.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.security.model.UserSecurity;
import ua.vixdev.gym.user.controller.dto.CreateUserDto;
import ua.vixdev.gym.user.controller.dto.GetUserDto;
import ua.vixdev.gym.user.controller.dto.UpdateUserDto;
import ua.vixdev.gym.user.exceptions.UserVisibleException;
import ua.vixdev.gym.user.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    private final UserSecurity userSecurity;

    /**
     * This method is used to find a list of users.
     *
     * @param firstName The first parameter for the search criteria by firstName and is optional.
     * @param lastName  The second parameter for the search criteria by lastName and is optional.
     * @param visible   The third parameter for the search criteria by visible and is optional.
     * @return List<User> Returns all users according to the criteria or without them with status 200(OK).
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    @Secured({"ROLE_ADMIN"})
    public List<GetUserDto> getAllUsers(
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
     *
     * @param id This is a parameter for the search criteria by ID and is required.
     * @return User Returns the specified user by ID with status 200(OK).
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @Cacheable(cacheNames = {"users"}, key = "#id")
    public GetUserDto findUserById(@PathVariable("id") Long id, @AuthenticationPrincipal Long principalId) {
        final GetUserDto requestedUser;
        if (Objects.equals(id, principalId)) {
            requestedUser = userService.findUserById(id);
            return requestedUser;
        }
        final var authorizedUser = userService.findUserById(principalId);
         if (userSecurity.isAdmin(authorizedUser)){
             requestedUser = userService.findUserById(id);
             return requestedUser;
         }
        log.warn("The authenticated User: %s, does not have access to the resource".formatted(authorizedUser.getEmail()));
        throw new AccessDeniedException("The authenticated User: %s, does not have access to the resource".formatted(authorizedUser.getEmail()));
    }

    /**
     * This method is used to create a new user.
     *
     * @param createUserDto This parameter represents a new user.
     * @return Returns a new user along with the user's location with status 201(CREATED).
     */

    @PostMapping()
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.CREATED)
    public GetUserDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        return userService.createNewUser(createUserDto);
    }

    /**
     * This method is used to update a user.
     *
     * @param id      This is a parameter for the search criteria by ID.
     * @param updateUserDto This parameter represents the updated user.
     * @return Returns the updated user with status 202(ACCEPTED).
     */

    @PutMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    @CachePut(cacheNames = {"users"}, key = "#id")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GetUserDto updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDto updateUserDto) {
        return userService.updateUser(id, updateUserDto);
    }

    /**
     * This method is used to update the user's visibility.
     *
     * @param id   This is a parameter for the search criteria by ID.
     * @param body This parameter represents the updated user's visibility.
     * @return Returns status 202(ACCEPTED).
     */

    @PatchMapping("/{id}/visible")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CacheEvict(cacheNames = {"users"}, key = "#id")
    public void updateUserVisibility(@PathVariable Long id,
                                     @RequestBody Map<String, String> body) {

        var visible = body.get("visible");
        if ((StringUtils.equalsIgnoreCase(visible, "true") ||
                StringUtils.equalsIgnoreCase(visible, "false"))) {
            visible = visible.toLowerCase();
            userService.updateUserVisibility(id, visible);
            return;
        }
        log.error("Unknown visibility value: {}!", visible);
        throw new UserVisibleException(visible);
    }

    /**
     * This method is used to delete a user.
     *
     * @param id This is a parameter for the search criteria by ID.
     * @return Returns a status of 204(NO_CONTENT).
     */

    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = {"users"}, key = "#id")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    /**
     * This method is used to clear cache.
     *
     * @return Returns a status 200(OK).
     */
    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true)})
    @GetMapping("/clearCache")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    public void clearUsersCache() {
    }
}
