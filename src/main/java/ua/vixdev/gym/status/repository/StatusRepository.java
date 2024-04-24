package ua.vixdev.gym.status.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.vixdev.gym.status.entity.Status;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status,Long> {
    @Query(
            " SELECT u FROM Status u " +
                    " WHERE " +
                    " u.visible = ?1")
    List<Status> findAllByVisible(@Param("visible") boolean visible);

    Optional<Status> findByValue(String value);
}
