package ua.vixdev.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vixdev.gym.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
