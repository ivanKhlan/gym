package ua.vixdev.gym.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.upload.entity.FolderTypesEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderTypesRepository extends JpaRepository<FolderTypesEntity, Long> {
    Optional<FolderTypesEntity> findByIdAndVisibleIsTrue(Long folderId);

    List<FolderTypesEntity> getAllByVisibleIsTrue();


}
