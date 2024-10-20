package com.medilink.api.controllers;

import com.medilink.api.dto.bed.BedRequestDTO;
import com.medilink.api.dto.bed.BedResponseDTO;
import com.medilink.api.models.Bed;
import com.medilink.api.enums.BedType;
import com.medilink.api.services.BedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BedControllerTest {

    @InjectMocks
    private BedController bedController;

    @Mock
    private BedService bedService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBedsByWardId_shouldReturnListOfBeds() {
        // Arrange
        String wardId = "ward123";
        List<Bed> beds = new ArrayList<>();
        beds.add(new Bed("bed1", wardId, null, BedType.AVAILABLE));
        beds.add(new Bed("bed2", wardId, null, BedType.RECEIVED));

        when(bedService.findBedsByWardId(wardId)).thenReturn(beds);

        // Act
        ResponseEntity<List<Bed>> response = bedController.getBedsByWardId(wardId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Bed> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertEquals("bed1", responseBody.get(0).getBedId());
        assertEquals(BedType.AVAILABLE, responseBody.get(0).getStatus());
    }

    @Test
    void updateBed_shouldReturnUpdatedBed() {
        // Arrange
        BedRequestDTO bedRequestDTO = new BedRequestDTO();
        bedRequestDTO.setBedId("bed1");
        bedRequestDTO.setStatus(BedType.RECEIVED);

        Bed updatedBed = new Bed("bed1", "ward123", null, BedType.RECEIVED);
        BedResponseDTO bedResponseDTO = new BedResponseDTO();
        bedResponseDTO.setBedId("bed1");
        bedResponseDTO.setStatus(BedType.RECEIVED);

        when(bedService.updateBed(bedRequestDTO)).thenReturn(updatedBed);
        when(modelMapper.map(updatedBed, BedResponseDTO.class)).thenReturn(bedResponseDTO);

        // Act
        ResponseEntity<BedResponseDTO> response = bedController.updateBed(bedRequestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        BedResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("bed1", responseBody.getBedId());
        assertEquals(BedType.RECEIVED, responseBody.getStatus());
    }

    @Test
    void emptyBed_shouldReturnUpdatedBed() {
        // Arrange
        String bedId = "bed1";
        Bed updatedBed = new Bed(bedId, "ward123", null, BedType.AVAILABLE);
        BedResponseDTO bedResponseDTO = new BedResponseDTO();
        bedResponseDTO.setBedId(bedId);
        bedResponseDTO.setStatus(BedType.AVAILABLE);

        when(bedService.emptyBed(bedId)).thenReturn(updatedBed);
        when(modelMapper.map(updatedBed, BedResponseDTO.class)).thenReturn(bedResponseDTO);

        // Act
        ResponseEntity<BedResponseDTO> response = bedController.updateBed(bedId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        BedResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("bed1", responseBody.getBedId());
        assertEquals(BedType.AVAILABLE, responseBody.getStatus());
    }

    @Test
    void deleteBed_shouldReturnNoContent_whenBedDeleted() {
        // Arrange
        String bedId = "bed1";
        when(bedService.deleteBed(bedId)).thenReturn(true); // Correct stubbing for a non-void method

        // Act
        ResponseEntity<Void> response = bedController.deleteBed(bedId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bedService, times(1)).deleteBed(bedId);
    }

    @Test
    void deleteBed_shouldReturnNotFound_whenBedDoesNotExist() {
        // Arrange
        String bedId = "bed1";
        when(bedService.deleteBed(bedId)).thenReturn(false); // Correct stubbing for non-void method

        // Act
        ResponseEntity<Void> response = bedController.deleteBed(bedId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bedService, times(1)).deleteBed(bedId);
    }




}
