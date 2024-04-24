package ua.vixdev.gym.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.user.controller.dto.RoleDto;
import ua.vixdev.gym.user.service.UserRoleService;

import java.util.List;

/**
 * Controller for managing roles in the gym application.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    /**
     * Retrieves all roles.
     *
     * @return ResponseEntity with the list of RoleDto objects and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles(){
        List<RoleDto> roles =  userRoleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    /**
     * Retrieves a role by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return ResponseEntity with the RoleDto object and HTTP status OK if the role exists,
     *         otherwise returns HTTP status NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findRoleById(@PathVariable Long id){
        RoleDto role =  userRoleService.getRoleById(id);
        if(role != null){
            return ResponseEntity.ok(role);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates a role by its ID.
     *
     * @param id The ID of the role to update.
     * @param roleDto The RoleDto object containing the updated role information.
     * @return ResponseEntity with the updated RoleDto object and HTTP status OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RoleDto>  updateRoles(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        RoleDto role = userRoleService.updateRole(id, roleDto);
        return ResponseEntity.ok(role);
    }

    /**
     * Creates a new role.
     *
     * @param roleDto The RoleDto object containing the information of the role to be created.
     * @return ResponseEntity with the created RoleDto object and HTTP status CREATED if successful,
     *         otherwise returns HTTP status INTERNAL_SERVER_ERROR.
     */
    @PostMapping
    public ResponseEntity<RoleDto> createRoles(@RequestBody RoleDto roleDto){
        RoleDto createdRole =  userRoleService.createRole(roleDto);
        if(createdRole != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Deletes a role by its ID.
     *
     * @param id The ID of the role to delete.
     * @return ResponseEntity with HTTP status NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id){
        userRoleService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
