package com.medilink.api.controllers;

import com.medilink.api.dto.hospital.HospitalRequestDTO;
import com.medilink.api.dto.hospital.HospitalResponseDTO;
import com.medilink.api.models.Hospital;
import com.medilink.api.enums.HospitalType; // Ensure you import your enum here
import com.medilink.api.services.HospitalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HospitalControllerTest {

    private HospitalController hospitalController;
    private HospitalService hospitalService;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        hospitalService = mock(HospitalService.class);
        modelMapper = new ModelMapper();
        hospitalController = new HospitalController(hospitalService, modelMapper);
    }

    @Test
    void createHospital_shouldReturnCreatedHospital() {
        // Arrange
        HospitalRequestDTO requestDTO = new HospitalRequestDTO();
        requestDTO.setHospitalName("City Hospital");
        requestDTO.setHospitalEmail("city@example.com");
        requestDTO.setArea("Downtown");
        requestDTO.setContactNumber("1234567890");
        requestDTO.setNoDoctors(10);
        requestDTO.setHospitalType(HospitalType.PRIVATE_HOSPITAL);

        Hospital hospital = new Hospital("1", "City Hospital", "city@example.com", "Downtown", "1234567890", 10, HospitalType.PRIVATE_HOSPITAL);
        HospitalResponseDTO responseDTO = new HospitalResponseDTO("1", "City Hospital", "city@example.com", "Downtown", "1234567890", 10, HospitalType.PRIVATE_HOSPITAL);

        when(hospitalService.saveHospital(any(Hospital.class))).thenReturn(hospital);

        // Act
        ResponseEntity<HospitalResponseDTO> responseEntity = hospitalController.createHospital(requestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("City Hospital", responseEntity.getBody().getHospitalName());
    }

    @Test
    void getAllHospitals_shouldReturnListOfHospitals() {
        // Arrange
        Hospital hospital = new Hospital("1", "City Hospital", "city@example.com", "Downtown", "1234567890", 10, HospitalType.PRIVATE_HOSPITAL);
        List<Hospital> hospitals = Collections.singletonList(hospital);

        when(hospitalService.getAllHospitals()).thenReturn(hospitals);

        // Act
        ResponseEntity<List<HospitalResponseDTO>> responseEntity = hospitalController.getAllHospitals();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("City Hospital", responseEntity.getBody().get(0).getHospitalName());
    }

    @Test
    void getHospitalById_shouldReturnHospital_whenExists() {
        // Arrange
        Hospital hospital = new Hospital("1", "City Hospital", "city@example.com", "Downtown", "1234567890", 10, HospitalType.PRIVATE_HOSPITAL);

        when(hospitalService.getHospitalById("1")).thenReturn(hospital);

        // Act
        ResponseEntity<HospitalResponseDTO> responseEntity = hospitalController.getHospitalById("1");

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("City Hospital", responseEntity.getBody().getHospitalName());
    }

    @Test
    void getHospitalById_shouldReturnNotFound_whenDoesNotExist() {
        // Arrange
        when(hospitalService.getHospitalById("1")).thenReturn(null);

        // Act
        ResponseEntity<HospitalResponseDTO> responseEntity = hospitalController.getHospitalById("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void updateHospital_shouldReturnUpdatedHospital_whenExists() {
        // Arrange
        HospitalRequestDTO requestDTO = new HospitalRequestDTO();
        requestDTO.setHospitalName("Updated City Hospital");
        requestDTO.setHospitalEmail("updatedcity@example.com");
        requestDTO.setArea("Downtown");
        requestDTO.setContactNumber("1234567890");
        requestDTO.setNoDoctors(15);
        requestDTO.setHospitalType(HospitalType.PRIVATE_HOSPITAL);

        Hospital existingHospital = new Hospital("1", "City Hospital", "city@example.com", "Downtown", "1234567890", 10, HospitalType.PRIVATE_HOSPITAL);
        Hospital updatedHospital = new Hospital("1", "Updated City Hospital", "updatedcity@example.com", "Downtown", "1234567890", 15, HospitalType.PRIVATE_HOSPITAL);

        when(hospitalService.updateHospital(eq("1"), any(Hospital.class))).thenReturn(updatedHospital);

        // Act
        ResponseEntity<HospitalResponseDTO> responseEntity = hospitalController.updateHospital("1", requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Updated City Hospital", responseEntity.getBody().getHospitalName());
    }

    @Test
    void updateHospital_shouldReturnNotFound_whenDoesNotExist() {
        // Arrange
        HospitalRequestDTO requestDTO = new HospitalRequestDTO();
        requestDTO.setHospitalName("Updated City Hospital");
        requestDTO.setHospitalEmail("updatedcity@example.com");
        requestDTO.setArea("Downtown");
        requestDTO.setContactNumber("1234567890");
        requestDTO.setNoDoctors(15);
        requestDTO.setHospitalType(HospitalType.PRIVATE_HOSPITAL);

        when(hospitalService.updateHospital(eq("1"), any(Hospital.class))).thenReturn(null);

        // Act
        ResponseEntity<HospitalResponseDTO> responseEntity = hospitalController.updateHospital("1", requestDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void deleteHospital_shouldReturnNoContent_whenDeleted() {
        // Arrange
        when(hospitalService.deleteHospital("1")).thenReturn(true);

        // Act
        ResponseEntity<Void> responseEntity = hospitalController.deleteHospital("1");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void deleteHospital_shouldReturnNotFound_whenDoesNotExist() {
        // Arrange
        when(hospitalService.deleteHospital("1")).thenReturn(false);

        // Act
        ResponseEntity<Void> responseEntity = hospitalController.deleteHospital("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
