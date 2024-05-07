package ua.vixdev.gym.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vixdev.gym.application.entity.TypeEntity;

public interface TypeRepository extends JpaRepository<TypeEntity, Long> {
}
