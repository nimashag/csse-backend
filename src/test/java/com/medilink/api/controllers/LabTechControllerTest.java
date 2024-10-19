package com.medilink.api.controllers;

import com.medilink.api.dto.labtech.LabTechRequestDTO;
import com.medilink.api.dto.labtech.LabTechResponseDTO;
import com.medilink.api.models.LabTech;
import com.medilink.api.services.LabTechService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LabTechControllerTest {

    @InjectMocks
    private LabTechController labTechController;

    @Mock
    private LabTechService labTechService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createLabTech_shouldReturnCreatedLabTech() {
        // Arrange
        LabTechRequestDTO requestDto = new LabTechRequestDTO();
        requestDto.setName("John Doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setPassword("password123");

        LabTech labTech = new LabTech();
        labTech.setName("John Doe");
        labTech.setEmail("john.doe@example.com");
        labTech.setPassword("password123");

        LabTechResponseDTO responseDto = new LabTechResponseDTO();
        responseDto.setName("John Doe");
        responseDto.setEmail("john.doe@example.com");

        when(modelMapper.map(requestDto, LabTech.class)).thenReturn(labTech);
        when(labTechService.saveLabTech(labTech)).thenReturn(labTech);
        when(modelMapper.map(labTech, LabTechResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<LabTechResponseDTO> response = labTechController.createLabTech(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        LabTechResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("John Doe", responseBody.getName());
        assertEquals("john.doe@example.com", responseBody.getEmail());
    }

    @Test
    void getAllLabTechs_shouldReturnListOfLabTechs() {
        // Arrange
        LabTech labTech1 = new LabTech("1", "John Doe", "john.doe@example.com", "password1", "hospital1");
        LabTech labTech2 = new LabTech("2", "Jane Doe", "jane.doe@example.com", "password2", "hospital2");
        List<LabTech> labTechs = Arrays.asList(labTech1, labTech2);

        when(labTechService.getAllLabTechs()).thenReturn(labTechs);

        LabTechResponseDTO responseDto1 = new LabTechResponseDTO();
        responseDto1.setName("John Doe");
        responseDto1.setEmail("john.doe@example.com");

        LabTechResponseDTO responseDto2 = new LabTechResponseDTO();
        responseDto2.setName("Jane Doe");
        responseDto2.setEmail("jane.doe@example.com");

        when(modelMapper.map(labTech1, LabTechResponseDTO.class)).thenReturn(responseDto1);
        when(modelMapper.map(labTech2, LabTechResponseDTO.class)).thenReturn(responseDto2);

        // Act
        ResponseEntity<List<LabTechResponseDTO>> response = labTechController.getAllLabTechs();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<LabTechResponseDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertEquals("John Doe", responseBody.get(0).getName());
        assertEquals("Jane Doe", responseBody.get(1).getName());
    }

    @Test
    void getLabTechById_shouldReturnLabTech_whenLabTechExists() {
        // Arrange
        LabTech labTech = new LabTech("1", "John Doe", "john.doe@example.com", "password1", "hospital1");

        LabTechResponseDTO responseDto = new LabTechResponseDTO();
        responseDto.setName("John Doe");
        responseDto.setEmail("john.doe@example.com");

        when(labTechService.getLabTechById("1")).thenReturn(labTech);
        when(modelMapper.map(labTech, LabTechResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<LabTechResponseDTO> response = labTechController.getLabTechById("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        LabTechResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("John Doe", responseBody.getName());
    }

    @Test
    void getLabTechById_shouldReturnNotFound_whenLabTechDoesNotExist() {
        // Arrange
        when(labTechService.getLabTechById("1")).thenReturn(null);

        // Act
        ResponseEntity<LabTechResponseDTO> response = labTechController.getLabTechById("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateLabTech_shouldReturnUpdatedLabTech_whenLabTechExists() {
        // Arrange
        LabTechRequestDTO requestDto = new LabTechRequestDTO();
        requestDto.setName("John Smith");
        requestDto.setEmail("john.smith@example.com");

        LabTech existingLabTech = new LabTech("1", "John Doe", "john.doe@example.com", "password1", "hospital1");
        LabTech updatedLabTech = new LabTech("1", "John Smith", "john.smith@example.com", "password2", "hospital2");

        when(labTechService.updateLabTech("1", modelMapper.map(requestDto, LabTech.class))).thenReturn(updatedLabTech);

        LabTechResponseDTO responseDto = new LabTechResponseDTO();
        responseDto.setName("John Smith");
        responseDto.setEmail("john.smith@example.com");

        when(modelMapper.map(updatedLabTech, LabTechResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<LabTechResponseDTO> response = labTechController.updateLabTech("1", requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        LabTechResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("John Smith", responseBody.getName());
        assertEquals("john.smith@example.com", responseBody.getEmail());
    }

    @Test
    void updateLabTech_shouldReturnNotFound_whenLabTechDoesNotExist() {
        // Arrange
        LabTechRequestDTO requestDto = new LabTechRequestDTO();
        requestDto.setName("John Doe");
        requestDto.setEmail("john.doe@example.com");

        when(labTechService.updateLabTech("1", modelMapper.map(requestDto, LabTech.class))).thenReturn(null);

        // Act
        ResponseEntity<LabTechResponseDTO> response = labTechController.updateLabTech("1", requestDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteLabTech_shouldReturnNoContent_whenLabTechDeleted() {
        // Arrange
        when(labTechService.deleteLabTech("1")).thenReturn(true);

        // Act
        ResponseEntity<Void> response = labTechController.deleteLabTech("1");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteLabTech_shouldReturnNotFound_whenLabTechDoesNotExist() {
        // Arrange
        when(labTechService.deleteLabTech("1")).thenReturn(false);

        // Act
        ResponseEntity<Void> response = labTechController.deleteLabTech("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
