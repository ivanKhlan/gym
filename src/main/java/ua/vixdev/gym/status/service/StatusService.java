package ua.vixdev.gym.status.service;

import ua.vixdev.gym.options.dto.CreateOptionDto;
import ua.vixdev.gym.options.dto.UpdateOptionDto;
import ua.vixdev.gym.options.entity.Options;
import ua.vixdev.gym.status.dto.CreateStatusDto;
import ua.vixdev.gym.status.dto.UpdateStatusDto;
import ua.vixdev.gym.status.entity.Status;

import java.util.List;

public interface StatusService {
    Status findByValue(String value);
    List<Status> findAllStatuses();
    List<Status> findStatusByVisible(boolean visible);
    Status findStatusById(Long id);
    Status createStatus(CreateStatusDto createStatusDto);
    Status updateStatus(Long id, UpdateStatusDto updateStatusDto);
    void deleteStatusById(Long id);
    void updateStatusVisibility(Long id, String visible);
}
