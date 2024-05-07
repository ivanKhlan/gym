package ua.vixdev.gym.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.user.controller.dto.UserHistoryDto;
import ua.vixdev.gym.user.entity.UserHistoryEntity;
import ua.vixdev.gym.user.service.UserHistoryService;

import java.util.List;

@RestController
@RequestMapping("/userHistories")
@RequiredArgsConstructor
public class UserHistoryController {

    private static final Long EMPTY_ID = null;
    private final UserHistoryService userHistoryService;

    /**
     * Retrieves all history changes
     * @return List of history changes
     */
    @GetMapping
    public List<UserHistoryEntity> findAll() {
        return userHistoryService.findAll();
    }

    /**
     * Retrieve history change by its id
     * @param id - represents id of history change in database
     * @return HistoryChangesResponseDto that represents history change
     */
    @GetMapping("/{id}")
    public UserHistoryEntity findById(@PathVariable Long id) {
        return userHistoryService.findById(id);
    }

    /**
     * Creates history change
     * @param userHistoryDto - represents history change
     * @return HistoryChangesResponseDto that represents history change
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserHistoryEntity createUserHistory(@RequestBody @Valid UserHistoryDto userHistoryDto) {
        return userHistoryService.createUserHistory(mapToUserHistory(EMPTY_ID, userHistoryDto));
    }

    /**
     * Updates history change
     * @param id - represents id of history change in database
     * @param userHistoryDto - represents history change
     * @return HistoryChangesResponseDto that represents history change
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserHistoryEntity updateUserHistory(@PathVariable Long id, @RequestBody @Valid UserHistoryDto userHistoryDto) {
        return userHistoryService.updateUserHistory(mapToUserHistory(id, userHistoryDto));
    }

    /**
     * Deletes history change by its id
     * @param id - represents id of history change in database
     * @return String as a successful removal
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        userHistoryService.deleteById(id);
    }

    private UserHistoryEntity mapToUserHistory(Long id, UserHistoryDto userHistoryDto) {
        return UserHistoryEntity.builder()
                .id(id)
                .userId(userHistoryDto.getUserId())
                .text(userHistoryDto.getText())
                .build();
    }
}
