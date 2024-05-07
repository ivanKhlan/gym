package ua.vixdev.gym.account.exception;

import ua.vixdev.gym.commons.excetpion.ResourceNotFoundException;

public class AccountNotFoundException extends ResourceNotFoundException {
    public AccountNotFoundException(Long id) {
        super("Account with ID: %s not found!".formatted(id));
    }
}
