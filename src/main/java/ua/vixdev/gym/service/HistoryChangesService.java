package ua.vixdev.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.dto.HistoryChangesRequestDto;
import ua.vixdev.gym.entity.HistoryChangesEntity;
import ua.vixdev.gym.exception.HistoryChangesNotFound;
import ua.vixdev.gym.repository.HistoryChangesRepository;

import java.time.LocalDateTime;
import java.util.List;
/**
 * Service for managing business logic requests in database
 */
@Service
@RequiredArgsConstructor
public class HistoryChangesService {

    private final HistoryChangesRepository historyChangesRepository;

    /**
     * Retrieves all history changes
     * @return List of HistoryChanges
     */
    @Transactional
    public List<HistoryChangesEntity> getAllHistoryChanges() {
        return historyChangesRepository.findAll();
    }

    /**
     * Retrieves history change by its id
     * @param id - represents id of history change
     * @return found HistoryChangesEntity
     */
    @Transactional
    public HistoryChangesEntity getHistoryChangesEntityById(Long id) {
        return historyChangesRepository.findById(id)
                .orElseThrow(
                        () -> new HistoryChangesNotFound("history changes with id '%d' was not found".formatted(id))
                );
    }

    /**
     * Creates HistoryChangesEntity
     * @param historyChangesRequestDto - represents request of history change
     * @return created HistoryChangesEntity
     */
    @Transactional
    public HistoryChangesEntity createHistoryChange(HistoryChangesRequestDto historyChangesRequestDto) {
        HistoryChangesEntity createdHistoryChange = HistoryChangesEntity.builder()
                .userId(historyChangesRequestDto.getUserId())
                .text(historyChangesRequestDto.getText())
                .createdAt(LocalDateTime.now())
                .build();

        return historyChangesRepository.save(createdHistoryChange);
    }

    /**
     * Updates history change by given id and HistoryChangesRequestDto
     * @param id - represents id of history change
     * @param historyChangesRequestDto - represents data for changing
     * @return updated HistoryChangesEntity
     */
    @Transactional
    public HistoryChangesEntity updateHistoryChange(Long id, HistoryChangesRequestDto historyChangesRequestDto) {
        HistoryChangesEntity historyChangesEntityById = getHistoryChangesEntityById(id);

        historyChangesEntityById.setText(historyChangesRequestDto.getText());
        historyChangesEntityById.setUserId(historyChangesRequestDto.getUserId());

        historyChangesRepository.save(historyChangesEntityById);

        return historyChangesEntityById;
    }

    /**
     * Deletes history change by given id
     * @param id - represents id of history change
     */
    @Transactional
    public void deleteHistoryChangeById(Long id) {
        historyChangesRepository.deleteById(id);
    }
}
