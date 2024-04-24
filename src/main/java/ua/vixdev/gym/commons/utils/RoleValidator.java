package ua.vixdev.gym.commons.utils;

import ua.vixdev.gym.user.entity.UserRoleEntity;
import ua.vixdev.gym.exception.RoleIsEmptyException;

import ua.vixdev.gym.exception.RoleValueIsEmptyException;
import ua.vixdev.gym.exception.RoleValueIsNullException;

public class RoleValidator {

    public static void roleValidate(UserRoleEntity role) {

        if (role == null) {

            throw new RoleIsEmptyException();

        } else if (role.getValue() == null) {

            throw new RoleValueIsNullException();

        } else if (role.getValue().isEmpty()) {

            throw new RoleValueIsEmptyException();

        }
    }
}

