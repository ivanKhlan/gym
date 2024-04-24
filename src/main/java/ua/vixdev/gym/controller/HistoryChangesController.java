package ua.vixdev.gym.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.dto.HistoryChangesRequestDto;
import ua.vixdev.gym.dto.HistoryChangesResponseDto;
import ua.vixdev.gym.entity.HistoryChangesEntity;
import ua.vixdev.gym.mapper.HistoryChangesMapper;
import ua.vixdev.gym.service.HistoryChangesService;

import java.util.List;

@RestController
@RequestMapping("history_changes")
@RequiredArgsConstructor
public class HistoryChangesController {

    private final HistoryChangesService historyChangesService;

    private final HistoryChangesMapper historyChangesMapper;

    private static final String GET_ALL_HISTORY_CHANGES = "/";

    private static final String GET_HISTORY_CHANGE_BY_ID = "/{id}";

    private static final String CREATE_HISTORY_CHANGE = "/";

    private static final String UPDATE_HISTORY_CHANGE_BY_ID = "/{id}";

    private static final String DELETE_HISTORY_CHANGE_BY_ID = "/{id}";


    /**
     * Retrieves all history changes
     * @return List of history changes
     */
    @GetMapping(GET_ALL_HISTORY_CHANGES)
    public ResponseEntity<List<HistoryChangesResponseDto>> getAllHistoryChanges() {
        List<HistoryChangesResponseDto> response = historyChangesService
                .getAllHistoryChanges()
                .stream()
                .map(historyChangesMapper::makeHistoryChangesDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve history change by its id
     * @param id - represents id of history change in database
     * @return HistoryChangesResponseDto that represents history change
     */
    @GetMapping(GET_HISTORY_CHANGE_BY_ID)
    public ResponseEntity<HistoryChangesResponseDto> getHistoryChangeById(@PathVariable Long id) {
        HistoryChangesEntity foundHistoryChangesEntity = historyChangesService.getHistoryChangesEntityById(id);

        HistoryChangesResponseDto response = historyChangesMapper.makeHistoryChangesDto(foundHistoryChangesEntity);

        return ResponseEntity.ok(response);
    }

    /**
     * Creates history change
     * @param historyChangesRequestDto - represents history change
     * @return HistoryChangesResponseDto that represents history change
     */
    @PostMapping(CREATE_HISTORY_CHANGE)
    public ResponseEntity<HistoryChangesResponseDto> createHistoryChange(@RequestBody HistoryChangesRequestDto historyChangesRequestDto) {
        HistoryChangesEntity createdHistoryChange = historyChangesService.createHistoryChange(historyChangesRequestDto);

        HistoryChangesResponseDto response = historyChangesMapper.makeHistoryChangesDto(createdHistoryChange);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates history change
     * @param id - represents id of history change in database
     * @param historyChangesRequestDto - represents history change
     * @return HistoryChangesResponseDto that represents history change
     */
    @PutMapping(UPDATE_HISTORY_CHANGE_BY_ID)
    public ResponseEntity<HistoryChangesResponseDto> updateHistoryChangeDto(@PathVariable Long id, @RequestBody HistoryChangesRequestDto historyChangesRequestDto) {
        HistoryChangesEntity historyChangesEntity = historyChangesService.updateHistoryChange(id, historyChangesRequestDto);

        HistoryChangesResponseDto response = historyChangesMapper.makeHistoryChangesDto(historyChangesEntity);

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes history change by its id
     * @param id - represents id of history change in database
     * @return String as a successful removal
     */
    @DeleteMapping(DELETE_HISTORY_CHANGE_BY_ID)
    public ResponseEntity<String> deleteHistoryChangeById(@PathVariable Long id) {
        historyChangesService.deleteHistoryChangeById(id);

        return ResponseEntity.ok("history change with id '%d' was deleted".formatted(id));
    }
}
