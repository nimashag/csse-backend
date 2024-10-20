package com.medilink.api.controllers;

import com.medilink.api.dto.WardHead.WardHeadRequestDTO;
import com.medilink.api.dto.WardHead.WardHeadResponseDTO;
import com.medilink.api.models.Ward;
import com.medilink.api.models.WardHead;
import com.medilink.api.services.WardHeadService;
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

public class WardHeadControllerTest {

    @InjectMocks
    private WardHeadController wardHeadController;

    @Mock
    private WardHeadService wardHeadService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWardHead_shouldReturnCreatedWardHead() {
        // Arrange
        WardHeadRequestDTO requestDto = new WardHeadRequestDTO();
        requestDto.setName("Dr. Alex Ward");
        requestDto.setEmail("alex.ward@example.com");
        requestDto.setPassword("password123");
        requestDto.setWardId("ward123");

        WardHead wardHead = new WardHead();
        wardHead.setName("Dr. Alex Ward");
        wardHead.setEmail("alex.ward@example.com");

        WardHeadResponseDTO responseDto = new WardHeadResponseDTO();
        responseDto.setName("Dr. Alex Ward");
        responseDto.setEmail("alex.ward@example.com");

        when(modelMapper.map(requestDto, WardHead.class)).thenReturn(wardHead);
        when(wardHeadService.saveWardHead(wardHead)).thenReturn(wardHead);
        when(modelMapper.map(wardHead, WardHeadResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<WardHeadResponseDTO> response = wardHeadController.createWardHead(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        WardHeadResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Dr. Alex Ward", responseBody.getName());
        assertEquals("alex.ward@example.com", responseBody.getEmail());
    }

    @Test
    void getAllWardHeads_shouldReturnListOfWardHeads() {
        // Arrange
        WardHead wardHead1 = new WardHead("1", "Dr. Alex Ward", "alex.ward@example.com", "password1", new Ward());
        WardHead wardHead2 = new WardHead("2", "Dr. Jamie Ward", "jamie.ward@example.com", "password2", new Ward());
        List<WardHead> wardHeads = Arrays.asList(wardHead1, wardHead2);

        when(wardHeadService.getAllWardHead()).thenReturn(wardHeads);

        WardHeadResponseDTO responseDto1 = new WardHeadResponseDTO();
        responseDto1.setName("Dr. Alex Ward");
        responseDto1.setEmail("alex.ward@example.com");

        WardHeadResponseDTO responseDto2 = new WardHeadResponseDTO();
        responseDto2.setName("Dr. Jamie Ward");
        responseDto2.setEmail("jamie.ward@example.com");

        when(modelMapper.map(wardHead1, WardHeadResponseDTO.class)).thenReturn(responseDto1);
        when(modelMapper.map(wardHead2, WardHeadResponseDTO.class)).thenReturn(responseDto2);

        // Act
        ResponseEntity<List<WardHeadResponseDTO>> response = wardHeadController.getAllWardHead();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<WardHeadResponseDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertEquals("Dr. Alex Ward", responseBody.get(0).getName());
        assertEquals("Dr. Jamie Ward", responseBody.get(1).getName());
    }

    @Test
    void getWardHeadById_shouldReturnWardHead_whenWardHeadExists() {
        // Arrange
        WardHead wardHead = new WardHead("1", "Dr. Alex Ward", "alex.ward@example.com", "password1", new Ward());

        WardHeadResponseDTO responseDto = new WardHeadResponseDTO();
        responseDto.setName("Dr. Alex Ward");
        responseDto.setEmail("alex.ward@example.com");

        when(wardHeadService.getWardHead("1")).thenReturn(wardHead);
        when(modelMapper.map(wardHead, WardHeadResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<WardHeadResponseDTO> response = wardHeadController.getWardHead("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        WardHeadResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Dr. Alex Ward", responseBody.getName());
    }

    @Test
    void getWardHeadById_shouldReturnNotFound_whenWardHeadDoesNotExist() {
        // Arrange
        when(wardHeadService.getWardHead("1")).thenReturn(null);

        // Act
        ResponseEntity<WardHeadResponseDTO> response = wardHeadController.getWardHead("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateWardHead_shouldReturnUpdatedWardHead_whenWardHeadExists() {
        // Arrange
        WardHeadRequestDTO requestDto = new WardHeadRequestDTO();
        requestDto.setName("Dr. Alex Smith");
        requestDto.setEmail("alex.smith@example.com");
        requestDto.setPassword("password123");

        WardHead existingWardHead = new WardHead("1", "Dr. Alex Ward", "alex.ward@example.com", "password1", new Ward());
        WardHead updatedWardHead = new WardHead("1", "Dr. Alex Smith", "alex.smith@example.com", "password456", new Ward());

        when(wardHeadService.updateWardHead("1", modelMapper.map(requestDto, WardHead.class))).thenReturn(updatedWardHead);

        WardHeadResponseDTO responseDto = new WardHeadResponseDTO();
        responseDto.setName("Dr. Alex Smith");
        responseDto.setEmail("alex.smith@example.com");

        when(modelMapper.map(updatedWardHead, WardHeadResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<WardHeadResponseDTO> response = wardHeadController.updateWardHead("1", requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        WardHeadResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Dr. Alex Smith", responseBody.getName());
        assertEquals("alex.smith@example.com", responseBody.getEmail());
    }

    @Test
    void updateWardHead_shouldReturnNotFound_whenWardHeadDoesNotExist() {
        // Arrange
        WardHeadRequestDTO requestDto = new WardHeadRequestDTO();
        requestDto.setName("Dr. Alex Ward");
        requestDto.setEmail("alex.ward@example.com");

        when(wardHeadService.updateWardHead("1", modelMapper.map(requestDto, WardHead.class))).thenReturn(null);

        // Act
        ResponseEntity<WardHeadResponseDTO> response = wardHeadController.updateWardHead("1", requestDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteWardHead_shouldReturnNoContent_whenWardHeadDeleted() {
        // Arrange
        when(wardHeadService.deleteWardHead("1")).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = wardHeadController.deleteWardHead("1");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteWardHead_shouldReturnNotFound_whenWardHeadDoesNotExist() {
        // Arrange
        when(wardHeadService.deleteWardHead("1")).thenReturn(false);

        // Act
        ResponseEntity<Boolean> response = wardHeadController.deleteWardHead("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
