package ua.vixdev.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.entity.Files;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilesRepository extends JpaRepository<Files, Long> {

    Optional<Files> findByIdAndVisibleIsTrue(Long fileId);

    List<Files> getAllByVisibleIsTrueAndFolderId(Long folderId);
}
