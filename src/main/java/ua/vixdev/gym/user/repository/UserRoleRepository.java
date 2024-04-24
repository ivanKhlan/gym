package ua.vixdev.gym.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.user.entity.UserRoleEntity;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity,Long> {
}
