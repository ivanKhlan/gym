package ua.vixdev.gym.status.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.status.dto.CreateStatusDto;
import ua.vixdev.gym.status.dto.UpdateStatusDto;
import ua.vixdev.gym.status.entity.Status;
import ua.vixdev.gym.status.exceptions.StatusVisibleException;
import ua.vixdev.gym.status.service.StatusService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/status")
public class StatusController {


    private final StatusService statusService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    List<Status> getAllStatuses() {
        return statusService.findAllStatuses();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Status findUserById(@PathVariable Long id) {
        return statusService.findStatusById(id);
    }

    @PostMapping()
    ResponseEntity<?> createStatus(@RequestBody @Valid CreateStatusDto statusDto) {
        var status = statusService.createStatus(statusDto);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody @Valid UpdateStatusDto statusDto) {
        var status = statusService.updateStatus(id,statusDto);
        return new ResponseEntity<>(status, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}/visible")
    ResponseEntity<?> updateStatusVisibility(@PathVariable Long id,
                                           @RequestBody Map<String, String> body) {

        var visible = body.get("visible");
        if (StringUtils.isNotBlank(visible)
                && (StringUtils.equalsIgnoreCase(visible, "true") ||
                StringUtils.equalsIgnoreCase(visible, "false"))) {
            visible = visible.toLowerCase();
            statusService.updateStatusVisibility(id, visible);
            return ResponseEntity.accepted().build();
        }
        log.error("Incorrect data was provided when update the status visibility: {}!", visible);
        throw new StatusVisibleException(visible);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteStatus(@PathVariable Long id) {
        statusService.deleteStatusById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
