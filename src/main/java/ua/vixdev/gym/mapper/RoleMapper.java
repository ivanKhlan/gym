package ua.vixdev.gym.mapper;

import org.springframework.stereotype.Component;
import ua.vixdev.gym.dto.RoleDto;
import ua.vixdev.gym.entity.Role;
import ua.vixdev.gym.exception.RoleIsEmptyException;

import java.time.LocalDateTime;

/**
 * Mapper class responsible for mapping between EntityService and ServiceDto objects.
 */
@Component
public class RoleMapper {

    /**
     * Maps a Role entity to a RoleDto.
     *
     * @param role The Role entity to be mapped.
     * @return The corresponding RoleDto.
     * @throws RoleIsEmptyException if the provided Role is null.
     */
    public RoleDto roleToDto(Role role){
        if(role == null){
            throw new RoleIsEmptyException();
        }
        return RoleDto.builder()
                .visible(role.getVisible())
                .value(role.getValue())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .deletedAt(role.getDeletedAt())
                .build();
    }

    /**
     * Maps a RoleDto to a Role entity.
     *
     * @param roleDto The RoleDto to be mapped.
     * @return The corresponding Role entity.
     * @throws RoleIsEmptyException if the provided RoleDto is null.
     */
    public Role dtoToRole(RoleDto roleDto){
        if(roleDto == null){
            throw new RoleIsEmptyException();
        }
        return Role.builder()
                .value(roleDto.getValue())
                .visible(roleDto.getVisible())
                .updatedAt(roleDto.getUpdatedAt())
                .createdAt(roleDto.getCreatedAt())
                .visible(roleDto.getVisible())
                .deletedAt(roleDto.getDeletedAt())
                .build();
    }

    /**
     * Updates a Role entity with values from a RoleDto.
     *
     * @param role The Role entity to be updated.
     * @param roleDto The RoleDto containing updated values.
     * @return The updated Role entity.
     * @throws RoleIsEmptyException if the provided RoleDto is null.
     */
    public Role updateService(Role role, RoleDto roleDto){
        if(roleDto == null){
            throw new RoleIsEmptyException();
        }

        Role updatedRole =  dtoToRole(roleDto);

        String value;
        Boolean visible;
        LocalDateTime createdAt;

        if(updatedRole.getValue() != null && !updatedRole.getValue().isEmpty()) {
            value = updatedRole.getValue();
        }else {
            value = role.getValue();
        }

        if (updatedRole.getVisible() != null){
            visible = updatedRole.getVisible();
        }else{
            visible = role.getVisible();
        }

        if(updatedRole.getCreatedAt() != null){
            createdAt = updatedRole.getCreatedAt();
        }else {
            createdAt = role.getCreatedAt();
        }

        return Role.builder()
                .updatedAt(LocalDateTime.now())
                .createdAt(createdAt)
                .value(value)
                .visible(visible)
                .build();

    }

    /**
     * Marks a Role entity as soft deleted by setting the deletedAt field.
     *
     * @param role The Role entity to be soft deleted.
     * @return The Role entity with the deletedAt field set to the current date and time.
     * @throws RoleIsEmptyException if the provided Role is null.
     */
    public Role softDeleted(Role role){
        if(role == null){
            throw new RoleIsEmptyException();
        }
        return Role.builder()
                .id(role.getId())
                .deletedAt(LocalDateTime.now()).build();
    }
}
