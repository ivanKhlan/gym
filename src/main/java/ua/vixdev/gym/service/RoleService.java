package ua.vixdev.gym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.dto.RoleDto;
import ua.vixdev.gym.exception.RoleIsEmptyException;
import ua.vixdev.gym.exception.RoleNotFoundException;
import ua.vixdev.gym.mapper.RoleMapper;
import ua.vixdev.gym.repository.RoleRepository;
import ua.vixdev.gym.utils.RoleValidator;

import java.util.List;

/**
 * Service class responsible for managing role-related operations.
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    /**
     * Retrieves all roles.
     *
     * @return List of RoleDto objects representing all roles.
     */
    public List<RoleDto> getAllRoles(){
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::roleToDto)
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
        return roleRepository.findById(id)
                .map(roleMapper::roleToDto)
                .orElseThrow(RoleNotFoundException::new);
    }

    /**
     * Deletes a role by its ID.
     *
     * @param id The ID of the role to delete.
     * @throws RoleNotFoundException if the role with the specified ID is not found.
     */
    public void deleteRole(Long id){
        var role = roleRepository
                .findById(id)
                .orElseThrow(RoleNotFoundException::new);

        role = roleMapper.softDeleted(role);

        roleRepository.save(role);
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

        var role = roleMapper.dtoToRole(roleDto);

        RoleValidator.roleValidate(role);

        role = roleRepository.save(role);

        return roleMapper.roleToDto(role);
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
        var oldRole = roleRepository
                .findById(id)
                .orElseThrow(RoleNotFoundException::new);

        var updatedRole = roleMapper.updateService(oldRole,roleDto);

        updatedRole.setId(id);

        updatedRole =  roleRepository.save(updatedRole);

        return roleMapper.roleToDto(updatedRole);
    }
}
