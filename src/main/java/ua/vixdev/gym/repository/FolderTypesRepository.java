package ua.vixdev.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vixdev.gym.entity.FolderTypes;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface FolderTypesRepository extends JpaRepository<FolderTypes, Long> {

    Optional<FolderTypes> findById(Long folderId);
    Optional<FolderTypes> findByIdAndVisibleIsTrue(Long folderId);

    List<FolderTypes> getAllByVisibleIsTrue();


}
