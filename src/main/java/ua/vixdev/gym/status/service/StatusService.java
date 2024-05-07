package ua.vixdev.gym.status.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vixdev.gym.status.entity.StatusEntity;
import ua.vixdev.gym.status.exceptions.StatusAlreadyExists;
import ua.vixdev.gym.status.exceptions.StatusNotFoundException;
import ua.vixdev.gym.status.repository.StatusRepository;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class StatusService {
    private final StatusRepository statusRepository;


    public List<StatusEntity> findAllStatuses() {
        return statusRepository.findAll();
    }


    public StatusEntity findStatusById(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new StatusNotFoundException(id));
    }

    @Transactional
    public StatusEntity createStatus(StatusEntity status) {
        validateStatusExists(status.getValue());
        return statusRepository.save(status);
    }

    @Transactional
    public StatusEntity updateStatus(StatusEntity status) {
        return statusRepository.findById(status.getId())
                .map(loadCustomer -> {
                    validateStatusValueExists(loadCustomer, status);
                    return statusRepository.save(status);
                }).orElseThrow(() -> new StatusNotFoundException(status.getId()));
    }

    public void deleteStatusById(Long id) {
        validateStatusExists(id);
        statusRepository.deleteById(id);
    }

    private void validateStatusExists(String value) {
        if (statusRepository.findByValue(value).isPresent()) {
            throw new StatusAlreadyExists(value);
        }
    }

    private void validateStatusExists(Long id) {
        statusRepository.findById(id).orElseThrow(() -> new StatusNotFoundException(id));
    }

    private void validateStatusValueExists(StatusEntity loadStatus, StatusEntity status) {
        if (statusRepository.existsByValue(status.getValue()) && !loadStatus.equalValue(status.getValue())) {
            throw new StatusAlreadyExists(status.getValue());
        }
    }
}
