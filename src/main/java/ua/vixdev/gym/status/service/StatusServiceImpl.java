package ua.vixdev.gym.status.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.status.dto.CreateStatusDto;
import ua.vixdev.gym.status.dto.UpdateStatusDto;
import ua.vixdev.gym.status.entity.Status;
import ua.vixdev.gym.status.exceptions.StatusAlreadyExists;
import ua.vixdev.gym.status.exceptions.StatusNotFoundException;
import ua.vixdev.gym.status.repository.StatusRepository;

import java.util.List;
@RequiredArgsConstructor
@Slf4j
@Service
public class StatusServiceImpl implements StatusService{
    private final StatusRepository statusRepository;

    @Override
    public Status findByValue(String value) {
        return statusRepository.findByValue(value).orElse(null);
    }

    @Override
    public List<Status> findAllStatuses() {
        return printLogInfo(statusRepository.findAll());
    }

    @Override
    public List<Status> findStatusByVisible(boolean visible) {
        return printLogInfo(statusRepository.findAllByVisible(visible));
    }

    @Override
    public Status findStatusById(Long id) {
        var loadStatus = statusRepository.findById(id);
        log.info("Loaded status with ID: {}", id);

        return loadStatus.orElseThrow(() -> {
            log.error("Status with ID: {} not found!", id);
            return new StatusNotFoundException(id);
        });
    }
@Transactional
    @Override
    public Status createStatus(CreateStatusDto createStatusDto) {
        var statusEntity = createStatusDto.fromDto();
        Status savedStatus = statusRepository.save(statusEntity);
        log.info("Saved option: {}", savedStatus);
        return savedStatus;
    }
    @Transactional
    @Override
    public Status updateStatus(Long id, UpdateStatusDto updateStatusDto) {
        var loadStatus = findStatusById(id);
        checkIfValueAlreadyExists(updateStatusDto.getValue());
        return updateStatusFields(loadStatus,updateStatusDto);
    }

    @Override
    public void deleteStatusById(Long id) {
        findStatusById(id);
        statusRepository.deleteById(id);
        log.info("Deleted status by ID: {}", id);
    }
    @Transactional
    @Override
    public void updateStatusVisibility(Long id, String visible) {
        var status = findStatusById(id);
        status.changeVisibility(visible);
        log.info("For a status with ID {}, the visibility has been changed to: {}", id, visible);
    }
    private Status updateStatusFields(Status loadStatus, UpdateStatusDto updateStatusDto) {
        var updatedStatus = loadStatus.updateFields(updateStatusDto);
        log.info("Updated status: {}", updatedStatus);
        return updatedStatus;
    }

    private void checkIfValueAlreadyExists(String value) {
        if (statusRepository.findByValue(value).isPresent()) {
            log.error("Status with key: {} is already registered", value);
            throw new StatusAlreadyExists(value);
        }

    }

    private List<Status> printLogInfo(List<Status> statuses) {
        log.info("Size of loaded statuses from database: {}", statuses.size());
        return statuses;
    }
}
