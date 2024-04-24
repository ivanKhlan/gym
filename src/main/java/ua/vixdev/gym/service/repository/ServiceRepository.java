package ua.vixdev.gym.service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.service.entity.EntityService;

@Repository
public interface ServiceRepository extends JpaRepository<EntityService, Long> {


}
