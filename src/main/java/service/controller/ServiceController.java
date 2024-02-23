package service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dto.ServiceDto;
import service.services.ServicesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ServiceController {

    private final ServicesService servicesService;

    @GetMapping("/services")
    ResponseEntity<List<ServiceDto>> getAllServices(){
        List<ServiceDto> services =  servicesService.getAllService();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
   ResponseEntity<ServiceDto> findServiceById(@PathVariable Long id){
        ServiceDto service =  servicesService.getServiceById(id);
        return ResponseEntity.ok(service);
    }

   @PutMapping("/{id}")
   ResponseEntity<ServiceDto>  updateService(@PathVariable Long id, @RequestBody ServiceDto serviceDto) {
       ServiceDto service = servicesService.updateService(id,serviceDto);
        return ResponseEntity.ok(service);
    }

    @PostMapping()
    ResponseEntity<ServiceDto> createService(@RequestBody ServiceDto serviceDto){
        servicesService.createService(serviceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceDto);
    }


    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteService(@PathVariable Long id){
        servicesService.deleteService(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
