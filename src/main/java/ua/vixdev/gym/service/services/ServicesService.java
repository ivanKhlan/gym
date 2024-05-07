package ua.vixdev.gym.service.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.service.dto.ServiceDto;
import ua.vixdev.gym.service.entity.ServiceEntity;
import ua.vixdev.gym.service.exceptions.ServiceIsEmptyException;
import ua.vixdev.gym.service.exceptions.ServiceNotFoundException;
import ua.vixdev.gym.service.repository.ServiceRepository;
import ua.vixdev.gym.service.validator.ServiceValidator;

import java.util.List;

/**
 * Service class responsible for handling business logic related to services.
 */
@Service
@RequiredArgsConstructor
public class ServicesService {

    private final ServiceRepository serviceRepository;


    public List<ServiceEntity> findAll() {
        return serviceRepository.findAll();
    }

    public ServiceEntity findById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(ServiceNotFoundException::new);
    }

    public ServiceEntity createService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    public ServiceEntity updateService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    public void deleteById(Long id) {
        serviceRepository.deleteById(id);
    }
}
