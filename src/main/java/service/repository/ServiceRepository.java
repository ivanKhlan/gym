package service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.entity.EntityService;

@Repository
public interface ServiceRepository extends JpaRepository<EntityService, Long> {


}
