package ua.vixdev.gym.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.user.entity.UserRoleEntity;
import ua.vixdev.gym.user.exceptions.RoleAlreadyExists;
import ua.vixdev.gym.user.exceptions.RoleNotFoundException;
import ua.vixdev.gym.user.repository.UserRoleRepository;

import java.util.List;

/**
 * Service class responsible for managing role-related operations.
 */
@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;


    public List<UserRoleEntity> getAllRoles() {
        return userRoleRepository.findAll();
    }

    public UserRoleEntity findRoleById(Long id) {
        return userRoleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

    public UserRoleEntity createRole(UserRoleEntity role) {
        validateRoleValueExists(role.getValue());
        return userRoleRepository.save(role);
    }

    public UserRoleEntity updateRole(UserRoleEntity role) {
        return userRoleRepository.findById(role.getId())
                .map(loadCustomer -> {
                    validateRoleValueExists(loadCustomer, role);
                    return userRoleRepository.save(role);
                }).orElseThrow(() -> new RoleNotFoundException(role.getId()));
    }

    public void deleteRole(Long id) {
        userRoleRepository.deleteById(id);
    }

    private void validateRoleValueExists(String value) {
        if (userRoleRepository.existsByValue(value)) {
            throw new RoleAlreadyExists(value);
        }
    }
    private void validateRoleValueExists(UserRoleEntity loadRole, UserRoleEntity role) {
        if (userRoleRepository.existsByValue(role.getValue()) && !loadRole.equalValue(role.getValue())) {
            throw new RoleAlreadyExists(role.getValue());
        }
    }
}
