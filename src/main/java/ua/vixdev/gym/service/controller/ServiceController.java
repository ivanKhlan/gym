package ua.vixdev.gym.service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vixdev.gym.service.dto.ServiceDto;
import ua.vixdev.gym.service.entity.ServiceEntity;
import ua.vixdev.gym.service.services.ServicesService;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * Controller class for managing services.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/services")
public class ServiceController {

    private static final Long EMPTY_ID = null;
    private final ServicesService servicesService;

    // Retrieve all services
    @GetMapping
    public List<ServiceEntity> findAll(){
        return servicesService.findAll();
    }

    // Find a service by its identifier
    @GetMapping("/{id}")
    public ServiceEntity findById(@PathVariable Long id){
        return servicesService.findById(id);
    }

    // Create a new service
    @PostMapping
    @ResponseStatus(CREATED)
    public ServiceEntity createService(@RequestBody @Valid ServiceDto serviceDto){
        return servicesService.createService(mapToService(EMPTY_ID, serviceDto));
    }

    // Update service information
    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    public ServiceEntity updateService(@PathVariable Long id, @RequestBody @Valid ServiceDto serviceDto) {
        return servicesService.updateService(mapToService(id, serviceDto));
    }

    // Delete a service by its identifier
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        servicesService.deleteById(id);
    }

    private ServiceEntity mapToService(Long id, ServiceDto serviceDto) {
        return ServiceEntity.builder()
                .id(id)
                .counts(serviceDto.getCounts())
                .description(serviceDto.getDescription())
                .title(serviceDto.getTitle())
                .image(serviceDto.getImage())
                .text(serviceDto.getText())
                .price(serviceDto.getPrice())
                .build();
    }
}


