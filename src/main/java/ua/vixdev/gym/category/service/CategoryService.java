package ua.vixdev.gym.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.category.entity.CategoryEntity;
import ua.vixdev.gym.category.exception.CategoryNotFoundException;
import ua.vixdev.gym.category.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryEntity findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    public CategoryEntity createCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    public CategoryEntity updateCategory(CategoryEntity category) {
        validateCategoryExists(category.getId());
        return categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        validateCategoryExists(id);
        categoryRepository.deleteById(id);
    }

    private void validateCategoryExists(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
