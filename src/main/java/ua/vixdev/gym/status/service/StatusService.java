package ua.vixdev.gym.status.service;

import ua.vixdev.gym.status.dto.StatusDto;
import ua.vixdev.gym.status.entity.Status;

import java.util.List;

public interface StatusService {
    Status findByValue(String value);
    List<Status> findAllStatuses();
    List<Status> findStatusByVisible(boolean visible);
    Status findStatusById(Long id);
    Status createStatus(StatusDto statusDto);
    Status updateStatus(Long id, StatusDto updateStatusDto);
    void deleteStatusById(Long id);
    void updateStatusVisibility(Long id, String visible);
}
