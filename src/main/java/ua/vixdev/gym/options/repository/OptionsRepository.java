package ua.vixdev.gym.options.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.options.entity.OptionEntity;

import java.util.List;
import java.util.Optional;
@Repository
public interface OptionsRepository extends JpaRepository<OptionEntity, Long> {
    Optional<OptionEntity> findByKey(String key);

    List<OptionEntity> findAllByValue(String value);

    @Query(
            " SELECT u FROM OptionEntity u " +
                    " WHERE " +
                    " u.visible = ?1")
    List<OptionEntity> findAllByVisible(@Param("visible") boolean visible);

}
