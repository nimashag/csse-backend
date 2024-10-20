package com.medilink.api.controllers;

import com.medilink.api.dto.labtest.LabTestRequestDTO;
import com.medilink.api.dto.labtest.LabTestResponseDTO;
import com.medilink.api.services.LabTestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LabTestControllerTest {

    private LabTestController labTestController;
    private LabTestService labTestService;

    @BeforeEach
    void setUp() {
        labTestService = mock(LabTestService.class);
        labTestController = new LabTestController(labTestService);
    }

    @Test
    void createLabTest_shouldReturnCreatedLabTest() {
        // Arrange
        LabTestRequestDTO requestDTO = new LabTestRequestDTO("Blood Test", "Pending", "N/A", "hospital123", "patient456");
        LabTestResponseDTO responseDTO = new LabTestResponseDTO("test789", "Blood Test", "Pending", "N/A", "hospital123", "patient456");

        when(labTestService.createLabTest(any(LabTestRequestDTO.class))).thenReturn(responseDTO);

        // Act
        ResponseEntity<LabTestResponseDTO> responseEntity = labTestController.createLabTest(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Blood Test", responseEntity.getBody().getTestName());
    }

    @Test
    void updateLabTest_shouldReturnUpdatedLabTest() {
        // Arrange
        LabTestRequestDTO requestDTO = new LabTestRequestDTO("Urine Test", "Completed", "Negative", "hospital123", "patient456");
        LabTestResponseDTO responseDTO = new LabTestResponseDTO("test789", "Urine Test", "Completed", "Negative", "hospital123", "patient456");

        when(labTestService.updateLabTest(eq("test789"), any(LabTestRequestDTO.class))).thenReturn(responseDTO);

        // Act
        ResponseEntity<LabTestResponseDTO> responseEntity = labTestController.updateLabTest("test789", requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Urine Test", responseEntity.getBody().getTestName());
    }

    @Test
    void getLabTestById_shouldReturnLabTest_whenExists() {
        // Arrange
        LabTestResponseDTO responseDTO = new LabTestResponseDTO("test789", "Blood Test", "Pending", "N/A", "hospital123", "patient456");

        when(labTestService.getLabTestById("test789")).thenReturn(responseDTO);

        // Act
        ResponseEntity<LabTestResponseDTO> responseEntity = labTestController.getLabTestById("test789");

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Blood Test", responseEntity.getBody().getTestName());
    }

    @Test
    void deleteLabTest_shouldReturnNoContent_whenDeleted() {
        // Arrange
        when(labTestService.deleteLabTest("test789")).thenReturn(true);

        // Act
        ResponseEntity<Void> responseEntity = labTestController.deleteLabTest("test789");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void deleteLabTest_shouldReturnNotFound_whenDoesNotExist() {
        // Arrange
        when(labTestService.deleteLabTest("test789")).thenReturn(false);

        // Act
        ResponseEntity<Void> responseEntity = labTestController.deleteLabTest("test789");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getLabTestsByHospitalId_shouldReturnListOfLabTests() {
        // Arrange
        LabTestResponseDTO responseDTO = new LabTestResponseDTO("test789", "Blood Test", "Pending", "N/A", "hospital123", "patient456");
        List<LabTestResponseDTO> labTests = Collections.singletonList(responseDTO);

        when(labTestService.getLabTestsByHospitalId("hospital123")).thenReturn(labTests);

        // Act
        ResponseEntity<List<LabTestResponseDTO>> responseEntity = labTestController.getLabTestsByHospitalId("hospital123");

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("Blood Test", responseEntity.getBody().get(0).getTestName());
    }

    @Test
    void getAllLabTests_shouldReturnListOfLabTests() {
        // Arrange
        LabTestResponseDTO responseDTO = new LabTestResponseDTO("test789", "Blood Test", "Pending", "N/A", "hospital123", "patient456");
        List<LabTestResponseDTO> labTests = Collections.singletonList(responseDTO);

        when(labTestService.getAllLabTests()).thenReturn(labTests);

        // Act
        ResponseEntity<List<LabTestResponseDTO>> responseEntity = labTestController.getAllLabTests();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("Blood Test", responseEntity.getBody().get(0).getTestName());
    }
}
