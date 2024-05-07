package ua.vixdev.gym.category.exception;

import ua.vixdev.gym.commons.excetpion.ResourceNotFoundException;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(Long id) {
        super("Category with ID: %s not found!".formatted(id));
    }
}
