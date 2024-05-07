package ua.vixdev.gym.application.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.application.entity.ApplicationEntity;

/**
 * Repository interface for managing Application entities.
 */
@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

  List<ApplicationEntity> findAllByDeletedAtIsNull();

  Optional<ApplicationEntity> findByIdAndDeletedAtIsNull(int id);
}
