package ua.vixdev.gym.service.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.vixdev.gym.service.dto.ServiceDto;
import ua.vixdev.gym.service.exceptions.ServiceIsEmptyException;
import ua.vixdev.gym.service.exceptions.ServiceNotFoundException;
import ua.vixdev.gym.service.mapper.ServiceMapper;
import ua.vixdev.gym.service.repository.ServiceRepository;
import ua.vixdev.gym.service.validator.ServiceValidator;

import java.util.List;
/**
 * Service class responsible for handling business logic related to services.
 */
@Service
public class ServicesService {

    private final ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper;

    @Autowired
    public ServicesService(ServiceRepository serviceRepository,  ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }
    /**
     * Retrieves all services from the repository and maps them to ServiceDto objects.
     * @return List of ServiceDto objects representing all services.
     */
    public List<ServiceDto> getAllService(){
        return serviceRepository.findAll()
                .stream()
                .map(serviceMapper::serviceToDto)
                .toList();
    }
    /**
     * Retrieves a service by its ID from the repository and maps it to a ServiceDto object.
     * Throws a ServiceNotFoundException if the service with the specified ID is not found.
     * @param id The ID of the service to retrieve.
     * @return ServiceDto object representing the retrieved service.
     */
    public ServiceDto getServiceById(Long id){
        return serviceRepository.findById(id)
                .map(serviceMapper::serviceToDto)
                .orElseThrow(ServiceNotFoundException::new);
    }
    /**
     * Deletes a service by its ID from the repository.
     * Throws a ServiceNotFoundException if the service with the specified ID is not found.
     * @param id The ID of the service to delete.
     */
    public void deleteService(Long id){

       var entityService = serviceRepository
               .findById(id)
               .orElseThrow(ServiceNotFoundException::new);

       entityService = serviceMapper.softDeleted(entityService);

       serviceRepository.save(entityService);
    }
    /**
     * Creates a new service based on the provided ServiceDto object.
     * Throws a ServiceIsEmptyException if the provided ServiceDto is null.
     * @param serviceDto The ServiceDto object representing the service to create.
     * @return ServiceDto object representing the newly created service.
     */
    public ServiceDto createService(ServiceDto serviceDto){
        if(serviceDto == null){
            throw new ServiceIsEmptyException();
        }

        var entityService = serviceMapper.dtoToService(serviceDto);

        ServiceValidator.serviceValidate(entityService);

        entityService = serviceRepository.save(entityService);

        return serviceMapper.serviceToDto(entityService);
    }
    /**
     * Updates an existing service with the provided ID using data from the provided ServiceDto object.
     * Throws a ServiceNotFoundException if the service with the specified ID is not found.
     * @param id The ID of the service to update.
     * @param serviceDto The ServiceDto object containing the updated service data.
     * @return ServiceDto object representing the updated service.
     */
    public ServiceDto updateService(Long id, ServiceDto serviceDto){

       var oldService = serviceRepository
               .findById(id)
               .orElseThrow(ServiceNotFoundException::new);

       var updatedService = serviceMapper.updateService(oldService,serviceDto);

       updatedService.setId(id);

       updatedService =  serviceRepository.save(updatedService);

       return serviceMapper.serviceToDto(updatedService);
    }

}
