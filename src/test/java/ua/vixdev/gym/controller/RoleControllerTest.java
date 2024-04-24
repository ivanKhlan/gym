package ua.vixdev.gym.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.vixdev.gym.dto.RoleDto;
import ua.vixdev.gym.service.RoleService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllRoles() {
        // Arrange
        List<RoleDto> mockRoles = Collections.singletonList(new RoleDto());
        when(roleService.getAllRoles()).thenReturn(mockRoles);
        // Act
        ResponseEntity<List<RoleDto>> response = roleController.getAllRoles();
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRoles, response.getBody());
    }

    @Test
    void testFindRoleById_ExistingId() {
        // Arrange
        Long id = 1L;
        RoleDto mockRole = new RoleDto();
        when(roleService.getRoleById(id)).thenReturn(mockRole);
        // Act
        ResponseEntity<RoleDto> response = roleController.findRoleById(id);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRole, response.getBody());
    }

    @Test
    void testFindRoleById_NonExistingId() {
        // Arrange
        Long id = 1L;
        when(roleService.getRoleById(id)).thenReturn(null);
        // Act
        ResponseEntity<RoleDto> response = roleController.findRoleById(id);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateRoles() {
        // Arrange
        Long id = 1L;
        RoleDto roleDto = new RoleDto();
        when(roleService.updateRole(id, roleDto)).thenReturn(roleDto);
        // Act
        ResponseEntity<RoleDto> response = roleController.updateRoles(id, roleDto);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roleDto, response.getBody());
    }

    @Test
    void testCreateRoles() {
        // Arrange
        RoleDto roleDto = new RoleDto();
        when(roleService.createRole(roleDto)).thenReturn(roleDto);
        // Act
        ResponseEntity<RoleDto> response = roleController.createRoles(roleDto);
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(roleDto, response.getBody());
    }

    @Test
    void testCreateRoles_InternalServerError() {
        // Arrange
        RoleDto roleDto = new RoleDto();
        when(roleService.createRole(roleDto)).thenReturn(null);
        // Act
        ResponseEntity<RoleDto> response = roleController.createRoles(roleDto);
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDeleteRole() {
        // Arrange
        Long id = 1L;
        // Act
        ResponseEntity<?> response = roleController.deleteRole(id);
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(roleService, times(1)).deleteRole(id);
    }
}
