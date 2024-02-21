package ua.vixdev.gym.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.entity.Application;

/**
 * Repository interface for managing Application entities.
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

  List<Application> findAllByDeletedAtIsNull();

  Optional<Application> findByIdAndDeletedAtIsNull(int id);
}
