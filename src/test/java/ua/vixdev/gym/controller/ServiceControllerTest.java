package ua.vixdev.gym.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.vixdev.gym.service.controller.ServiceController;
import ua.vixdev.gym.service.dto.ServiceDto;
import ua.vixdev.gym.service.services.ServicesService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ServiceControllerTest {

    @Mock
    private ServicesService servicesService;

    @InjectMocks
    private ServiceController serviceController;

    private ServiceDto testServiceDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testServiceDto = new ServiceDto();
        testServiceDto.setTitle("Test Service");
    }

    @Test
    void testGetAllServices() {
        // Arrange
        when(servicesService.findAll()).thenReturn(Collections.singletonList(testServiceDto));
        // Act
        ResponseEntity<List<ServiceDto>> response = serviceController.findAll();
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Service", response.getBody().get(0).getTitle());
    }

    @Test
    void testFindServiceById() {
        // Arrange
        long id = 1L;
        when(servicesService.findById(id)).thenReturn(testServiceDto);
        // Act
        ResponseEntity<ServiceDto> response = serviceController.findById(id);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Service", response.getBody().getTitle());
    }

    @Test
    void testUpdateService() {
        // Arrange
        long id = 1L;
        when(servicesService.updateService(id, testServiceDto)).thenReturn(testServiceDto);
        // Act
        ResponseEntity<ServiceDto> response = serviceController.updateService(id, testServiceDto);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Service", response.getBody().getTitle());
    }

    @Test
    void testCreateService() {
        // Arrange
        when(servicesService.createService(testServiceDto)).thenReturn(testServiceDto);
        // Act
        ResponseEntity<ServiceDto> response = serviceController.createService(testServiceDto);
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test Service", response.getBody().getTitle());
    }

    @Test
    void testDeleteService() {
        // Act
        ResponseEntity<?> response = serviceController.deleteById(1L);
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(servicesService, times(1)).deleteById(1L);
    }
}
