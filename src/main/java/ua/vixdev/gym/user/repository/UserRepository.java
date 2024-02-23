package ua.vixdev.gym.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-02-21
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where u.email = ?1")
    Optional<UserEntity> findByEmailAddress(String email);

    @Query(
            " SELECT u FROM UserEntity u " +
                    " WHERE " +
                    " lower(u.firstName)  LIKE lower(concat('%', :firstName, '%')) " +
                    " AND lower(u.lastName) LIKE lower(concat('%', :lastName, '%')) "
    )
    List<UserEntity> findByFirstNameAndLastName(@Param("firstName") String firstName,
                                                @Param("lastName") String lastName);

    @Query(
            " SELECT u FROM UserEntity u " +
                    " WHERE " +
                    " lower(u.firstName)  LIKE lower(concat('%', :firstName, '%')) ")
    List<UserEntity> findByFirstName(@Param("firstName") String firstName);

    @Query(
            " SELECT u FROM UserEntity u " +
                    " WHERE " +
                    " lower(u.lastName)  LIKE lower(concat('%', :lastName, '%')) ")
    List<UserEntity> findByLastName(@Param("lastName") String lastName);

    @Query(
            " SELECT u FROM UserEntity u " +
                    " WHERE " +
                    " u.visible = ?1")
    List<UserEntity> findByVisible(@Param("visible") Boolean visible);

    UserEntity save(UserEntity entity);
}