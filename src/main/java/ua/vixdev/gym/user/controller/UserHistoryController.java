package ua.vixdev.gym.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.user.controller.dto.UserHistoryRequest;
import ua.vixdev.gym.user.controller.dto.UserHistoryResponse;
import ua.vixdev.gym.user.entity.UserHistoryEntity;
import ua.vixdev.gym.user.mapper.UserHistoryMapper;
import ua.vixdev.gym.user.service.UserHistoryService;

import java.util.List;

@RestController
@RequestMapping("/histories")
@RequiredArgsConstructor
public class UserHistoryController {

    private final UserHistoryService userHistoryService;
    private final UserHistoryMapper userHistoryMapper;


    /**
     * Retrieves all history changes
     * @return List of history changes
     */
    @GetMapping
    public ResponseEntity<List<UserHistoryResponse>> getAllHistoryChanges() {
        List<UserHistoryResponse> response = userHistoryService
                .getAllHistoryChanges()
                .stream()
                .map(userHistoryMapper::makeHistoryChangesDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve history change by its id
     * @param id - represents id of history change in database
     * @return HistoryChangesResponseDto that represents history change
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserHistoryResponse> getHistoryChangeById(@PathVariable Long id) {
        UserHistoryEntity foundUserHistoryEntity = userHistoryService.getHistoryChangesEntityById(id);

        UserHistoryResponse response = userHistoryMapper.makeHistoryChangesDto(foundUserHistoryEntity);

        return ResponseEntity.ok(response);
    }

    /**
     * Creates history change
     * @param userHistoryRequest - represents history change
     * @return HistoryChangesResponseDto that represents history change
     */
    @PostMapping
    public ResponseEntity<UserHistoryResponse> createHistoryChange(@RequestBody UserHistoryRequest userHistoryRequest) {
        UserHistoryEntity createdHistoryChange = userHistoryService.createHistoryChange(userHistoryRequest);

        UserHistoryResponse response = userHistoryMapper.makeHistoryChangesDto(createdHistoryChange);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates history change
     * @param id - represents id of history change in database
     * @param userHistoryRequest - represents history change
     * @return HistoryChangesResponseDto that represents history change
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserHistoryResponse> updateHistoryChangeDto(@PathVariable Long id, @RequestBody UserHistoryRequest userHistoryRequest) {
        UserHistoryEntity userHistoryEntity = userHistoryService.updateHistoryChange(id, userHistoryRequest);

        UserHistoryResponse response = userHistoryMapper.makeHistoryChangesDto(userHistoryEntity);

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes history change by its id
     * @param id - represents id of history change in database
     * @return String as a successful removal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistoryChangeById(@PathVariable Long id) {
        userHistoryService.deleteHistoryChangeById(id);

        return ResponseEntity.ok("history change with id '%d' was deleted".formatted(id));
    }
}
