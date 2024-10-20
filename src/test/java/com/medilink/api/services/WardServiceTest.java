package com.medilink.api.services;

import com.medilink.api.dto.ward.WardRequestDTO;
import com.medilink.api.dto.ward.WardResponseDTO;
import com.medilink.api.models.Bed;
import com.medilink.api.models.Hospital;
import com.medilink.api.models.Ward;
import com.medilink.api.repositories.BedRepository;
import com.medilink.api.repositories.WardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WardServiceTest {

    @InjectMocks
    private WardService wardService;

    @Mock
    private WardRepository wardRepository;

    @Mock
    private HospitalService hospitalService;

    @Mock
    private BedService bedService;

    @Mock
    private BedRepository bedRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWard_shouldCreateWardAndBeds() {
        // Arrange
        String hospitalId = "hospital1";
        String wardId = "ward1";
        int wardNo = 101;
        int bedCount = 5;

        Hospital hospital = new Hospital();
        hospital.setHospitalId(hospitalId);

        WardRequestDTO request = new WardRequestDTO();
        request.setHospitalId(hospitalId);
        request.setWardNo(wardNo);
        request.setBeds(bedCount);

        Ward ward = new Ward();
        ward.setWardId(wardId);
        ward.setWardNo(wardNo);
        ward.setHospital(hospital);

        List<Bed> beds = new ArrayList<>();
        for (int i = 0; i < bedCount; i++) {
            beds.add(new Bed());
        }

        when(hospitalService.getHospitalById(hospitalId)).thenReturn(hospital);
        when(wardRepository.save(any(Ward.class))).thenReturn(ward);
        when(bedService.createBeds(wardId, bedCount)).thenReturn(beds);

        // Act
        WardResponseDTO createdWard = wardService.createWard(request);

        // Assert
        assertNotNull(createdWard);
        assertEquals(wardId, createdWard.getWardId());
        assertEquals(hospital, createdWard.getHospital());
        assertEquals(wardNo, createdWard.getWardNo());
        assertEquals(bedCount, createdWard.getBeds().size());
    }

    @Test
    void createWard_shouldThrowExceptionWhenHospitalNotFound() {
        // Arrange
        String hospitalId = "hospital1";

        WardRequestDTO request = new WardRequestDTO();
        request.setHospitalId(hospitalId);

        when(hospitalService.getHospitalById(hospitalId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            wardService.createWard(request);
        });

        assertEquals("Hospital not found", exception.getMessage());
    }

    @Test
    void getWard_shouldReturnWardWhenFound() {
        // Arrange
        String wardId = "ward1";

        Ward ward = new Ward();
        ward.setWardId(wardId);

        when(wardRepository.findById(wardId)).thenReturn(Optional.of(ward));

        // Act
        Ward foundWard = wardService.getWard(wardId);

        // Assert
        assertNotNull(foundWard);
        assertEquals(wardId, foundWard.getWardId());
    }

    @Test
    void getWard_shouldThrowExceptionWhenWardNotFound() {
        // Arrange
        String wardId = "ward1";

        when(wardRepository.findById(wardId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            wardService.getWard(wardId);
        });

        assertEquals("Ward not found", exception.getMessage());
    }

    @Test
    void getHospitalWard_shouldReturnListOfWardsForHospital() {
        // Arrange
        String hospitalId = "hospital1";
        List<Ward> wards = new ArrayList<>();
        wards.add(new Ward());
        wards.add(new Ward());

        when(wardRepository.findByHospital_HospitalId(hospitalId)).thenReturn(wards);

        // Act
        List<Ward> result = wardService.getHospitalWard(hospitalId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void deleteWard_shouldDeleteWardAndAssociatedBeds() {
        // Arrange
        String wardId = "ward1";

        List<Bed> beds = new ArrayList<>();
        beds.add(new Bed());
        beds.add(new Bed());

        when(wardRepository.existsById(wardId)).thenReturn(true);
        when(bedService.findBedsByWardId(wardId)).thenReturn(beds);
        doNothing().when(bedRepository).deleteAll(beds);
        doNothing().when(wardRepository).deleteById(wardId);

        // Act
        boolean deleted = wardService.deleteWard(wardId);

        // Assert
        assertTrue(deleted);
        verify(bedRepository, times(1)).deleteAll(beds);
        verify(wardRepository, times(1)).deleteById(wardId);
    }

    @Test
    void deleteWard_shouldNotDeleteNonExistingWard() {
        // Arrange
        String wardId = "ward1";

        when(wardRepository.existsById(wardId)).thenReturn(false);

        // Act
        boolean deleted = wardService.deleteWard(wardId);

        // Assert
        assertFalse(deleted);
        verify(wardRepository, never()).deleteById(wardId);
    }
}
