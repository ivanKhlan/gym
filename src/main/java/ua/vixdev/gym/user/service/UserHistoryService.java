package ua.vixdev.gym.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.user.controller.dto.UserHistoryRequest;
import ua.vixdev.gym.user.entity.UserHistoryEntity;
import ua.vixdev.gym.exception.HistoryChangesNotFound;
import ua.vixdev.gym.user.repository.UserHistoryRepository;

import java.time.LocalDateTime;
import java.util.List;
/**
 * Service for managing business logic requests in database
 */
@Service
@RequiredArgsConstructor
public class UserHistoryService {

    private final UserHistoryRepository userHistoryRepository;

    /**
     * Retrieves all history changes
     * @return List of HistoryChanges
     */
    @Transactional
    public List<UserHistoryEntity> getAllHistoryChanges() {
        return userHistoryRepository.findAll();
    }

    /**
     * Retrieves history change by its id
     * @param id - represents id of history change
     * @return found HistoryChangesEntity
     */
    @Transactional
    public UserHistoryEntity getHistoryChangesEntityById(Long id) {
        return userHistoryRepository.findById(id)
                .orElseThrow(
                        () -> new HistoryChangesNotFound("history changes with id '%d' was not found".formatted(id))
                );
    }

    /**
     * Creates HistoryChangesEntity
     * @param userHistoryRequest - represents request of history change
     * @return created HistoryChangesEntity
     */
    @Transactional
    public UserHistoryEntity createHistoryChange(UserHistoryRequest userHistoryRequest) {
        UserHistoryEntity createdHistoryChange = UserHistoryEntity.builder()
                .userId(userHistoryRequest.getUserId())
                .text(userHistoryRequest.getText())
                .createdAt(LocalDateTime.now())
                .build();

        return userHistoryRepository.save(createdHistoryChange);
    }

    /**
     * Updates history change by given id and HistoryChangesRequestDto
     * @param id - represents id of history change
     * @param userHistoryRequest - represents data for changing
     * @return updated HistoryChangesEntity
     */
    @Transactional
    public UserHistoryEntity updateHistoryChange(Long id, UserHistoryRequest userHistoryRequest) {
        UserHistoryEntity userHistoryEntityById = getHistoryChangesEntityById(id);

        userHistoryEntityById.setText(userHistoryRequest.getText());
        userHistoryEntityById.setUserId(userHistoryRequest.getUserId());

        userHistoryRepository.save(userHistoryEntityById);

        return userHistoryEntityById;
    }

    /**
     * Deletes history change by given id
     * @param id - represents id of history change
     */
    @Transactional
    public void deleteHistoryChangeById(Long id) {
        userHistoryRepository.deleteById(id);
    }
}
