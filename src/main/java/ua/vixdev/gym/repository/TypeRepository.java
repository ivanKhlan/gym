package ua.vixdev.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vixdev.gym.entity.TypeEntity;

public interface TypeRepository extends JpaRepository<TypeEntity, Long> {
}
