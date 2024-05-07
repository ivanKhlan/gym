package ua.vixdev.gym.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.user.controller.dto.UserHistoryDto;
import ua.vixdev.gym.user.entity.UserHistoryEntity;
import ua.vixdev.gym.user.exceptions.UserHistoryNotFoundException;
import ua.vixdev.gym.user.exceptions.UserNotFoundException;
import ua.vixdev.gym.user.repository.UserHistoryRepository;
import ua.vixdev.gym.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
/**
 * Service for managing business logic requests in database
 */
@Service
@RequiredArgsConstructor
public class UserHistoryService {

    private final UserHistoryRepository userHistoryRepo;
    private final UserRepository userRepository;

    /**
     * Retrieves all history changes
     * @return List of HistoryChanges
     */
    public List<UserHistoryEntity> findAll() {
        return userHistoryRepo.findAll();
    }

    /**
     * Retrieves history change by its id
     * @param id - represents id of history change
     * @return found HistoryChangesEntity
     */
    public UserHistoryEntity findById(Long id) {
        return userHistoryRepo.findById(id)
                .orElseThrow(() -> new UserHistoryNotFoundException(id));
    }

    /**
     * Creates HistoryChangesEntity
     * @param userHistory - represents request of history change
     * @return created HistoryChangesEntity
     */
    @Transactional
    public UserHistoryEntity createUserHistory(UserHistoryEntity userHistory) {
        validateUserExists(userHistory.getUserId());
        return userHistoryRepo.save(userHistory);
    }

    /**
     * Updates history change by given id and HistoryChangesRequestDto
     * @param userHistory - represents data for changing
     * @return updated HistoryChangesEntity
     */
    @Transactional
    public UserHistoryEntity updateUserHistory(UserHistoryEntity userHistory) {
        validateUserExists(userHistory.getUserId());
        return userHistoryRepo.save(userHistory);
    }

    /**
     * Deletes history change by given id
     * @param id - represents id of history change
     */
    public void deleteById(Long id) {
        userHistoryRepo.deleteById(id);
    }

    private void validateUserExists(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }
    }
}
