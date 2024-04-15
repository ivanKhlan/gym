package ua.vixdev.gym.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vixdev.gym.user.entity.GenderEntity;
import ua.vixdev.gym.user.entity.UserEntity;

public interface GenderRepository extends JpaRepository<GenderEntity, Long> {

    GenderEntity save(GenderEntity entity);
}
