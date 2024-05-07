package ua.vixdev.gym.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.vixdev.gym.user.controller.dto.UserRoleDto;
import ua.vixdev.gym.user.service.UserRoleService;
import ua.vixdev.gym.user.controller.UserRoleController;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRoleControllerTest {

    @Mock
    private UserRoleService userRoleService;

    @InjectMocks
    private UserRoleController userRoleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllRoles() {
        // Arrange
        List<UserRoleDto> mockRoles = Collections.singletonList(new UserRoleDto());
        when(userRoleService.getAllRoles()).thenReturn(mockRoles);
        // Act
        ResponseEntity<List<UserRoleDto>> response = userRoleController.findAllRoles();
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRoles, response.getBody());
    }

    @Test
    void testFindRoleById_ExistingId() {
        // Arrange
        Long id = 1L;
        UserRoleDto mockRole = new UserRoleDto();
        when(userRoleService.findRoleById(id)).thenReturn(mockRole);
        // Act
        ResponseEntity<UserRoleDto> response = userRoleController.findRoleById(id);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRole, response.getBody());
    }

    @Test
    void testFindRoleById_NonExistingId() {
        // Arrange
        Long id = 1L;
        when(userRoleService.findRoleById(id)).thenReturn(null);
        // Act
        ResponseEntity<UserRoleDto> response = userRoleController.findRoleById(id);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateRoles() {
        // Arrange
        Long id = 1L;
        UserRoleDto userRoleDto = new UserRoleDto();
        when(userRoleService.updateRole(id, userRoleDto)).thenReturn(userRoleDto);
        // Act
        ResponseEntity<UserRoleDto> response = userRoleController.updateRoles(id, userRoleDto);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userRoleDto, response.getBody());
    }

    @Test
    void testCreateRoles() {
        // Arrange
        UserRoleDto userRoleDto = new UserRoleDto();
        when(userRoleService.createRole(userRoleDto)).thenReturn(userRoleDto);
        // Act
        ResponseEntity<UserRoleDto> response = userRoleController.createRoles(userRoleDto);
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userRoleDto, response.getBody());
    }

    @Test
    void testCreateRoles_InternalServerError() {
        // Arrange
        UserRoleDto userRoleDto = new UserRoleDto();
        when(userRoleService.createRole(userRoleDto)).thenReturn(null);
        // Act
        ResponseEntity<UserRoleDto> response = userRoleController.createRoles(userRoleDto);
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDeleteRole() {
        // Arrange
        Long id = 1L;
        // Act
        ResponseEntity<?> response = userRoleController.deleteRole(id);
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userRoleService, times(1)).deleteRole(id);
    }
}
