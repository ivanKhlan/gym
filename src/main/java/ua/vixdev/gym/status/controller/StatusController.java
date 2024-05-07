package ua.vixdev.gym.status.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.status.dto.StatusDto;
import ua.vixdev.gym.status.entity.StatusEntity;
import ua.vixdev.gym.status.service.StatusService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/status")
public class StatusController {

    private static final Long EMPTY_ID = null;
    private final StatusService statusService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    List<StatusEntity> getAllStatuses() {
        return statusService.findAllStatuses();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    StatusEntity findUserById(@PathVariable Long id) {
        return statusService.findStatusById(id);
    }

    @PostMapping()
    StatusEntity createStatus(@RequestBody @Valid StatusDto statusDto) {
        return statusService.createStatus(mapToStatus(EMPTY_ID, statusDto));
    }

    @PutMapping("/{id}")
    StatusEntity updateStatus(@PathVariable Long id, @RequestBody @Valid StatusDto statusDto) {
        return statusService.updateStatus(mapToStatus(id, statusDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteStatus(@PathVariable Long id) {
        statusService.deleteStatusById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private StatusEntity mapToStatus(Long id, StatusDto statusDto) {
        return StatusEntity.builder()
                .id(id)
                .value(statusDto.getValue())
                .visible(statusDto.isVisible())
                .build();
    }
}
