package ua.vixdev.gym.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vixdev.gym.user.entity.UserHistoryEntity;

public interface UserHistoryRepository extends JpaRepository<UserHistoryEntity, Long> {
}
