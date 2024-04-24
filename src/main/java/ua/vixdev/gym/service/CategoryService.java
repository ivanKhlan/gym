package ua.vixdev.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.entity.CategoryEntity;
import ua.vixdev.gym.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryEntity saveCategoryEntity(CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }

    public void deleteCategoryEntityById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Optional<CategoryEntity> findCategoryEntityById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<CategoryEntity> findAllCategoryEntities() {
        return categoryRepository.findAll();
    }
}
