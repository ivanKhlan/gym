package ua.vixdev.gym.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vixdev.gym.category.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
