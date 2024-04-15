package ua.vixdev.gym.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vixdev.gym.user.entity.GenderEntity;
import ua.vixdev.gym.user.entity.TypeEntity;

public interface TypeRepository extends JpaRepository<TypeEntity, Long>  {

    TypeEntity save(TypeEntity entity);

}
