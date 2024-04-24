package ua.vixdev.gym.utils;

import ua.vixdev.gym.entity.Role;
import ua.vixdev.gym.exception.RoleIsEmptyException;

import ua.vixdev.gym.exception.RoleValueIsEmptyException;
import ua.vixdev.gym.exception.RoleValueIsNullException;

public class RoleValidator {

    public static void roleValidate(Role role) {

        if (role == null) {

            throw new RoleIsEmptyException();

        } else if (role.getValue() == null) {

            throw new RoleValueIsNullException();

        } else if (role.getValue().isEmpty()) {

            throw new RoleValueIsEmptyException();

        }
    }
}

