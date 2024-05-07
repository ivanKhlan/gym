package ua.vixdev.gym.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.user.controller.dto.UserRoleDto;
import ua.vixdev.gym.user.entity.UserRoleEntity;
import ua.vixdev.gym.user.service.UserRoleService;

import java.util.List;

/**
 * Controller for managing roles in the gym application.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class UserRoleController {

    private static final Long EMPTY_ID = null;
    private final UserRoleService userRoleService;

    /**
     * Retrieves all roles.
     *
     * @return ResponseEntity with the list of RoleDto objects and HTTP status OK.
     */
    @GetMapping
    public List<UserRoleEntity> findAllRoles() {
        return userRoleService.getAllRoles();
    }

    /**
     * Retrieves a role by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return ResponseEntity with the RoleDto object and HTTP status OK if the role exists,
     * otherwise returns HTTP status NOT_FOUND.
     */
    @GetMapping("/{id}")
    public UserRoleEntity findRoleById(@PathVariable Long id) {
        return userRoleService.findRoleById(id);
    }

    /**
     * Creates a new role.
     *
     * @param userRoleDto The RoleDto object containing the information of the role to be created.
     * @return ResponseEntity with the created RoleDto object and HTTP status CREATED if successful,
     * otherwise returns HTTP status INTERNAL_SERVER_ERROR.
     */
    @PostMapping
    public UserRoleEntity createRoles(@RequestBody @Valid UserRoleDto userRoleDto) {
        return userRoleService.createRole(mapToUserRole(EMPTY_ID, userRoleDto));
    }

    /**
     * Updates a role by its ID.
     *
     * @param id          The ID of the role to update.
     * @param userRoleDto The RoleDto object containing the updated role information.
     * @return ResponseEntity with the updated RoleDto object and HTTP status OK.
     */
    @PutMapping("/{id}")
    public UserRoleEntity updateRoles(@PathVariable Long id, @RequestBody @Valid UserRoleDto userRoleDto) {
        return userRoleService.updateRole(mapToUserRole(id, userRoleDto));
    }

    /**
     * Deletes a role by its ID.
     *
     * @param id The ID of the role to delete.
     * @return ResponseEntity with HTTP status NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        userRoleService.deleteRole(id);
    }

    private UserRoleEntity mapToUserRole(Long id, UserRoleDto roleDto) {
        return UserRoleEntity.builder()
                .id(id)
                .value(roleDto.getValue())
                .visible(roleDto.getVisible())
                .build();
    }
}
