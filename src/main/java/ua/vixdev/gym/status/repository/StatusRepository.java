package ua.vixdev.gym.status.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.vixdev.gym.status.entity.StatusEntity;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<StatusEntity,Long> {
    @Query(
            " SELECT u FROM StatusEntity u " +
                    " WHERE " +
                    " u.visible = ?1")
    List<StatusEntity> findAllByVisible(@Param("visible") boolean visible);

    Optional<StatusEntity> findByValue(String value);

    boolean existsByValue(String value);
}
