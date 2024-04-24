package ua.vixdev.gym.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vixdev.gym.category.entity.TypeEntity;

public interface TypeRepository extends JpaRepository<TypeEntity, Long> {
}
