package ua.vixdev.gym.options.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.options.entity.Options;

import java.util.List;
import java.util.Optional;
@Repository
public interface OptionsRepository extends JpaRepository<Options, Long> {
    Optional<Options> findByKey(String key);

    List<Options> findAllByValue(String value);

    @Query(
            " SELECT u FROM Options u " +
                    " WHERE " +
                    " u.visible = ?1")
    List<Options> findAllByVisible(@Param("visible") boolean visible);

}
