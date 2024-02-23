package service.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.dto.ServiceDto;
import service.exceptions.ServiceIsEmptyException;
import service.exceptions.ServiceNotFoundException;
import service.mapper.ServiceMapper;
import service.repository.ServiceRepository;
import service.validator.ServiceValidator;

import java.util.List;

@Service
public class ServicesService {

    private final ServiceRepository serviceRepository;

    private final ServiceMapper serviceMapper;

    @Autowired
    public ServicesService(ServiceRepository serviceRepository,  ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    public List<ServiceDto> getAllService(){
        return serviceRepository.findAll()
                .stream()
                .map(serviceMapper::serviceToDto)
                .toList();
    }

    public ServiceDto getServiceById(Long id){
        return serviceRepository.findById(id)
                .map(serviceMapper::serviceToDto)
                .orElseThrow(ServiceNotFoundException::new);
    }

    public void deleteService(Long id){

       var entityService = serviceRepository
               .findById(id)
               .orElseThrow(ServiceNotFoundException::new);

       entityService = serviceMapper.softDeleted(entityService);

       serviceRepository.save(entityService);
    }

    public ServiceDto createService(ServiceDto serviceDto){
        if(serviceDto == null){
            throw new ServiceIsEmptyException();
        }

        var entityService = serviceMapper.dtoToService(serviceDto);

        ServiceValidator.serviceValidate(entityService);

        entityService = serviceRepository.save(entityService);

        return serviceMapper.serviceToDto(entityService);
    }

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
