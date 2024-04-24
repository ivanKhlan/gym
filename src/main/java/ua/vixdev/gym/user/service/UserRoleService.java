package ua.vixdev.gym.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.user.controller.dto.RoleDto;
import ua.vixdev.gym.exception.RoleIsEmptyException;
import ua.vixdev.gym.exception.RoleNotFoundException;
import ua.vixdev.gym.user.mapper.UserRoleMapper;
import ua.vixdev.gym.user.repository.UserRoleRepository;
import ua.vixdev.gym.commons.utils.RoleValidator;

import java.util.List;

/**
 * Service class responsible for managing role-related operations.
 */
@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    private final UserRoleMapper userRoleMapper;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository, UserRoleMapper userRoleMapper) {
        this.userRoleRepository = userRoleRepository;
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * Retrieves all roles.
     *
     * @return List of RoleDto objects representing all roles.
     */
    public List<RoleDto> getAllRoles(){
        return userRoleRepository.findAll()
                .stream()
                .map(userRoleMapper::roleToDto)
                .toList();
    }

    /**
     * Retrieves a role by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return The RoleDto object representing the retrieved role.
     * @throws RoleNotFoundException if the role with the specified ID is not found.
     */
    public RoleDto getRoleById(Long id){
        return userRoleRepository.findById(id)
                .map(userRoleMapper::roleToDto)
                .orElseThrow(RoleNotFoundException::new);
    }

    /**
     * Deletes a role by its ID.
     *
     * @param id The ID of the role to delete.
     * @throws RoleNotFoundException if the role with the specified ID is not found.
     */
    public void deleteRole(Long id){
        var role = userRoleRepository
                .findById(id)
                .orElseThrow(RoleNotFoundException::new);

        role = userRoleMapper.softDeleted(role);

        userRoleRepository.save(role);
    }

    /**
     * Creates a new role.
     *
     * @param roleDto The RoleDto object containing the information of the role to be created.
     * @return The RoleDto object representing the created role.
     * @throws RoleIsEmptyException if the provided RoleDto is null.
     */
    public RoleDto createRole(RoleDto roleDto){
        if(roleDto == null){
            throw new RoleIsEmptyException();
        }

        var role = userRoleMapper.dtoToRole(roleDto);

        RoleValidator.roleValidate(role);

        role = userRoleRepository.save(role);

        return userRoleMapper.roleToDto(role);
    }

    /**
     * Updates a role by its ID.
     *
     * @param id The ID of the role to update.
     * @param roleDto The RoleDto object containing the updated role information.
     * @return The RoleDto object representing the updated role.
     * @throws RoleNotFoundException if the role with the specified ID is not found.
     */
    public RoleDto updateRole(Long id, RoleDto roleDto){
        var oldRole = userRoleRepository
                .findById(id)
                .orElseThrow(RoleNotFoundException::new);

        var updatedRole = userRoleMapper.updateService(oldRole,roleDto);

        updatedRole.setId(id);

        updatedRole =  userRoleRepository.save(updatedRole);

        return userRoleMapper.roleToDto(updatedRole);
    }
}
