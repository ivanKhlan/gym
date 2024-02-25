package service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dto.ServiceDto;
import service.services.ServicesService;

import java.util.List;
/**
 * Controller class for managing services.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ServiceController {

    private final ServicesService servicesService;

    // Retrieve all services
    @GetMapping("/services")
    public ResponseEntity<List<ServiceDto>> getAllServices(){
        List<ServiceDto> services =  servicesService.getAllService();
        return ResponseEntity.ok(services);
    }

    // Find a service by its identifier
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDto> findServiceById(@PathVariable Long id){
        ServiceDto service =  servicesService.getServiceById(id);
        if(service != null){
            return ResponseEntity.ok(service);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Update service information
    @PutMapping("/{id}")
    public ResponseEntity<ServiceDto>  updateService(@PathVariable Long id, @RequestBody ServiceDto serviceDto) {
        ServiceDto service = servicesService.updateService(id, serviceDto);
        return ResponseEntity.ok(service);
    }

    // Create a new service
    @PostMapping
    public ResponseEntity<ServiceDto> createService(@RequestBody ServiceDto serviceDto){
        ServiceDto createdService =  servicesService.createService(serviceDto);
        if(createdService != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(serviceDto);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Delete a service by its identifier
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id){
        servicesService.deleteService(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


