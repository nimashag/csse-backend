package com.medilink.api.controllers;

import com.medilink.api.dto.receptionist.ReceptionistRequestDTO;
import com.medilink.api.dto.receptionist.ReceptionistResponseDTO;
import com.medilink.api.models.Receptionist;
import com.medilink.api.services.ReceptionistService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ReceptionistControllerTest {

    @InjectMocks
    private ReceptionistController receptionistController;

    @Mock
    private ReceptionistService receptionistService;

    @Mock
    private ModelMapper modelMapper;
    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAllReceptionists_shouldReturnListOfReceptionists() {
        // Arrange
        Receptionist receptionist1 = new Receptionist("1", "Jane Doe", "jane.doe@example.com", "password1", null);
        Receptionist receptionist2 = new Receptionist("2", "John Doe", "john.doe@example.com", "password2", null);
        List<Receptionist> receptionists = Arrays.asList(receptionist1, receptionist2);

        when(receptionistService.getAllReceptionists()).thenReturn(receptionists);

        ReceptionistResponseDTO responseDto1 = new ReceptionistResponseDTO();
        responseDto1.setName("Jane Doe");
        responseDto1.setEmail("jane.doe@example.com");

        ReceptionistResponseDTO responseDto2 = new ReceptionistResponseDTO();
        responseDto2.setName("John Doe");
        responseDto2.setEmail("john.doe@example.com");

        when(modelMapper.map(receptionist1, ReceptionistResponseDTO.class)).thenReturn(responseDto1);
        when(modelMapper.map(receptionist2, ReceptionistResponseDTO.class)).thenReturn(responseDto2);

        // Act
        ResponseEntity<List<ReceptionistResponseDTO>> response = receptionistController.getAllReceptionist();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ReceptionistResponseDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertEquals("Jane Doe", responseBody.get(0).getName());
        assertEquals("John Doe", responseBody.get(1).getName());
    }

    @Test
    void getReceptionistById_shouldReturnReceptionist_whenReceptionistExists() {
        // Arrange
        Receptionist receptionist = new Receptionist("1", "Jane Doe", "jane.doe@example.com", "password1", null);

        ReceptionistResponseDTO responseDto = new ReceptionistResponseDTO();
        responseDto.setName("Jane Doe");
        responseDto.setEmail("jane.doe@example.com");

        when(receptionistService.getReceptionist("1")).thenReturn(receptionist);
        when(modelMapper.map(receptionist, ReceptionistResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<ReceptionistResponseDTO> response = receptionistController.getReceptionistById("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReceptionistResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Jane Doe", responseBody.getName());
    }

    @Test
    void getReceptionistById_shouldReturnNotFound_whenReceptionistDoesNotExist() {
        // Arrange
        when(receptionistService.getReceptionist("1")).thenReturn(null);

        // Act
        ResponseEntity<ReceptionistResponseDTO> response = receptionistController.getReceptionistById("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateReceptionist_shouldReturnUpdatedReceptionist_whenReceptionistExists() {
        // Arrange
        ReceptionistRequestDTO requestDto = new ReceptionistRequestDTO();
        requestDto.setName("Jane Smith");
        requestDto.setEmail("jane.smith@example.com");
        requestDto.setPassword("password123");

        Receptionist existingReceptionist = new Receptionist("1", "Jane Doe", "jane.doe@example.com", "password1", null);
        Receptionist updatedReceptionist = new Receptionist("1", "Jane Smith", "jane.smith@example.com", "password2", null);

        when(receptionistService.updateReceptionist("1", modelMapper.map(requestDto, Receptionist.class))).thenReturn(updatedReceptionist);

        ReceptionistResponseDTO responseDto = new ReceptionistResponseDTO();
        responseDto.setName("Jane Smith");
        responseDto.setEmail("jane.smith@example.com");

        when(modelMapper.map(updatedReceptionist, ReceptionistResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<ReceptionistResponseDTO> response = receptionistController.updateReceptionist("1", requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReceptionistResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Jane Smith", responseBody.getName());
        assertEquals("jane.smith@example.com", responseBody.getEmail());
    }

    @Test
    void updateReceptionist_shouldReturnNotFound_whenReceptionistDoesNotExist() {
        // Arrange
        ReceptionistRequestDTO requestDto = new ReceptionistRequestDTO();
        requestDto.setName("Jane Doe");
        requestDto.setEmail("jane.doe@example.com");

        when(receptionistService.updateReceptionist("1", modelMapper.map(requestDto, Receptionist.class))).thenReturn(null);

        // Act
        ResponseEntity<ReceptionistResponseDTO> response = receptionistController.updateReceptionist("1", requestDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteReceptionist_shouldReturnNoContent_whenReceptionistDeleted() {
        // Arrange
        when(receptionistService.deleteReceptionist("1")).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = receptionistController.deleteReceptionist("1");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteReceptionist_shouldReturnNotFound_whenReceptionistDoesNotExist() {
        // Arrange
        when(receptionistService.deleteReceptionist("1")).thenReturn(false);

        // Act
        ResponseEntity<Boolean> response = receptionistController.deleteReceptionist("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
