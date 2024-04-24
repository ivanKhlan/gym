package ua.vixdev.gym.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.upload.entity.UploadFilesEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilesRepository extends JpaRepository<UploadFilesEntity, Long> {
    Optional<UploadFilesEntity> findByIdAndVisibleIsTrue(Long fileId);

    List<UploadFilesEntity> getAllByVisibleIsTrueAndFolderId(Long folderId);
}
