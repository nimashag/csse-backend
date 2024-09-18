package com.medilink.api.services;

import com.medilink.api.models.Hospital;
import com.medilink.api.enums.HospitalType;
import com.medilink.api.repositories.HospitalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HospitalServiceTest {

    @InjectMocks
    private HospitalService hospitalService;

    @Mock
    private HospitalRepository hospitalRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveHospital_shouldReturnSavedHospital() {
        // Arrange
        Hospital hospital = new Hospital();
        hospital.setHospitalId("1");
        hospital.setHospitalName("City Hospital");
        hospital.setHospitalEmail("city@example.com");
        hospital.setArea("Downtown");
        hospital.setContactNumber("1234567890");
        hospital.setHospitalType(HospitalType.PRIVATE_HOSPITAL);

        when(hospitalRepository.save(hospital)).thenReturn(hospital);

        // Act
        Hospital savedHospital = hospitalService.saveHospital(hospital);

        // Assert
        assertNotNull(savedHospital);
        assertEquals("1", savedHospital.getHospitalId());
        assertEquals("City Hospital", savedHospital.getHospitalName());
    }

    @Test
    void getAllHospitals_shouldReturnListOfHospitals() {
        // Arrange
        Hospital hospital1 = new Hospital("1", "City Hospital", "city@example.com", "Downtown", "1234567890", HospitalType.PRIVATE_HOSPITAL);
        Hospital hospital2 = new Hospital("2", "County Hospital", "county@example.com", "Uptown", "0987654321", HospitalType.PUBLIC_HOSPITAL);
        List<Hospital> hospitals = Arrays.asList(hospital1, hospital2);

        when(hospitalRepository.findAll()).thenReturn(hospitals);

        // Act
        List<Hospital> result = hospitalService.getAllHospitals();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("City Hospital", result.get(0).getHospitalName());
        assertEquals("County Hospital", result.get(1).getHospitalName());
    }

    @Test
    void getHospitalById_shouldReturnHospital_whenHospitalExists() {
        // Arrange
        Hospital hospital = new Hospital("1", "City Hospital", "city@example.com", "Downtown", "1234567890", HospitalType.PRIVATE_HOSPITAL);

        when(hospitalRepository.findById("1")).thenReturn(Optional.of(hospital));

        // Act
        Hospital result = hospitalService.getHospitalById("1");

        // Assert
        assertNotNull(result);
        assertEquals("City Hospital", result.getHospitalName());
    }

    @Test
    void getHospitalById_shouldReturnNull_whenHospitalDoesNotExist() {
        // Arrange
        when(hospitalRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        Hospital result = hospitalService.getHospitalById("1");

        // Assert
        assertNull(result);
    }

    @Test
    void updateHospital_shouldReturnUpdatedHospital_whenHospitalExists() {
        // Arrange
        Hospital existingHospital = new Hospital("1", "City Hospital", "city@example.com", "Downtown", "1234567890", HospitalType.PRIVATE_HOSPITAL);
        Hospital updatedHospital = new Hospital("1", "Updated City Hospital", "updatedcity@example.com", "Downtown", "1234567890", HospitalType.PRIVATE_HOSPITAL);

        when(hospitalRepository.findById("1")).thenReturn(Optional.of(existingHospital));
        when(hospitalRepository.save(updatedHospital)).thenReturn(updatedHospital);

        // Act
        Hospital result = hospitalService.updateHospital("1", updatedHospital);

        // Assert
        assertNotNull(result);
        assertEquals("Updated City Hospital", result.getHospitalName());
    }

    @Test
    void updateHospital_shouldReturnNull_whenHospitalDoesNotExist() {
        // Arrange
        Hospital updatedHospital = new Hospital("1", "Updated City Hospital", "updatedcity@example.com", "Downtown", "1234567890", HospitalType.PRIVATE_HOSPITAL);

        when(hospitalRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        Hospital result = hospitalService.updateHospital("1", updatedHospital);

        // Assert
        assertNull(result);
    }

    @Test
    void deleteHospital_shouldReturnTrue_whenHospitalExists() {
        // Arrange
        String hospitalId = "1";
        when(hospitalRepository.existsById(hospitalId)).thenReturn(true);

        // Act
        boolean result = hospitalService.deleteHospital(hospitalId);

        // Assert
        assertTrue(result);
        verify(hospitalRepository, times(1)).deleteById(hospitalId);
    }

    @Test
    void deleteHospital_shouldReturnFalse_whenHospitalDoesNotExist() {
        // Arrange
        String hospitalId = "1";
        when(hospitalRepository.existsById(hospitalId)).thenReturn(false);

        // Act
        boolean result = hospitalService.deleteHospital(hospitalId);

        // Assert
        assertFalse(result);
        verify(hospitalRepository, never()).deleteById(hospitalId);
    }
}
