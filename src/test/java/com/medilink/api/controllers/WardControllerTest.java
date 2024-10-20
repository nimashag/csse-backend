package com.medilink.api.controllers;

import com.medilink.api.dto.ward.WardRequestDTO;
import com.medilink.api.dto.ward.WardResponseDTO;
import com.medilink.api.models.Ward;
import com.medilink.api.services.WardService;
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

public class WardControllerTest {

    @InjectMocks
    private WardController wardController;

    @Mock
    private WardService wardService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWard_shouldReturnCreatedWard() {
        // Arrange
        WardRequestDTO requestDto = new WardRequestDTO();
        requestDto.setHospitalId("hospital123");
        requestDto.setWardNo(101);
        requestDto.setBeds(20);

        WardResponseDTO responseDto = new WardResponseDTO();
        responseDto.setWardId("ward123");
        responseDto.setWardNo(101);

        when(wardService.createWard(requestDto)).thenReturn(responseDto);

        // Act
        ResponseEntity<WardResponseDTO> response = wardController.createWard(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        WardResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("ward123", responseBody.getWardId());
        assertEquals(101, responseBody.getWardNo());
    }

    @Test
    void getWardById_shouldReturnWard_whenWardExists() {
        // Arrange
        Ward ward = new Ward("ward123", null, 101, null);
        WardResponseDTO responseDto = new WardResponseDTO();
        responseDto.setWardId("ward123");
        responseDto.setWardNo(101);

        when(wardService.getWard("ward123")).thenReturn(ward);
        when(modelMapper.map(ward, WardResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<WardResponseDTO> response = wardController.getWardById("ward123");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        WardResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("ward123", responseBody.getWardId());
        assertEquals(101, responseBody.getWardNo());
    }

    @Test
    void getWardById_shouldReturnNotFound_whenWardDoesNotExist() {
        // Arrange
        when(wardService.getWard("ward123")).thenReturn(null);

        // Act
        ResponseEntity<WardResponseDTO> response = wardController.getWardById("ward123");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getWardsByHospitalId_shouldReturnListOfWards() {
        // Arrange
        Ward ward1 = new Ward("ward123", null, 101, null);
        Ward ward2 = new Ward("ward124", null, 102, null);
        List<Ward> wards = Arrays.asList(ward1, ward2);

        WardResponseDTO responseDto1 = new WardResponseDTO();
        responseDto1.setWardId("ward123");
        responseDto1.setWardNo(101);

        WardResponseDTO responseDto2 = new WardResponseDTO();
        responseDto2.setWardId("ward124");
        responseDto2.setWardNo(102);

        when(wardService.getHospitalWard("hospital123")).thenReturn(wards);
        when(modelMapper.map(ward1, WardResponseDTO.class)).thenReturn(responseDto1);
        when(modelMapper.map(ward2, WardResponseDTO.class)).thenReturn(responseDto2);

        // Act
        ResponseEntity<List<WardResponseDTO>> response = wardController.getWardsByHospitalId("hospital123");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<WardResponseDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertEquals("ward123", responseBody.get(0).getWardId());
        assertEquals("ward124", responseBody.get(1).getWardId());
    }

    @Test
    void deleteWard_shouldReturnNoContent_whenWardDeleted() {
        // Arrange
        when(wardService.deleteWard("ward123")).thenReturn(true);

        // Act
        ResponseEntity<Void> response = wardController.deleteWard("ward123");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteWard_shouldReturnNotFound_whenWardDoesNotExist() {
        // Arrange
        when(wardService.deleteWard("ward123")).thenReturn(false);

        // Act
        ResponseEntity<Void> response = wardController.deleteWard("ward123");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
