package ua.vixdev.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vixdev.gym.entity.HistoryChangesEntity;

public interface HistoryChangesRepository extends JpaRepository<HistoryChangesEntity, Long> {
}
