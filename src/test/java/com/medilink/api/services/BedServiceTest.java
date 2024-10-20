package com.medilink.api.services;

import com.medilink.api.dto.bed.BedRequestDTO;
import com.medilink.api.dto.patient.PatientResponseDTO;
import com.medilink.api.enums.BedType;
import com.medilink.api.models.Bed;
import com.medilink.api.repositories.BedRepository;
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

class BedServiceTest {

    @InjectMocks
    private BedService bedService;

    @Mock
    private BedRepository bedRepository;

    @Mock
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBeds_shouldReturnListOfCreatedBeds() {
        // Arrange
        String wardId = "ward1";
        int numberOfBeds = 3;
        List<Bed> beds = new ArrayList<>();
        for (int i = 0; i < numberOfBeds; i++) {
            Bed bed = new Bed();
            bed.setWardId(wardId);
            bed.setStatus(BedType.AVAILABLE);
            beds.add(bed);
        }

        when(bedRepository.saveAll(anyList())).thenReturn(beds);

        // Act
        List<Bed> createdBeds = bedService.createBeds(wardId, numberOfBeds);

        // Assert
        assertNotNull(createdBeds);
        assertEquals(numberOfBeds, createdBeds.size());
        assertEquals(BedType.AVAILABLE, createdBeds.get(0).getStatus());
    }

    @Test
    void findBedsByWardId_shouldReturnBedsWithGivenWardId() {
        // Arrange
        String wardId = "ward1";
        List<Bed> beds = new ArrayList<>();
        beds.add(new Bed());
        beds.add(new Bed());

        when(bedRepository.findByWardId(wardId)).thenReturn(beds);

        // Act
        List<Bed> result = bedService.findBedsByWardId(wardId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void updateBed_shouldUpdateBedWithPatient() {
        // Arrange
        String bedId = "bed1";
        String patientId = "patient1";

        BedRequestDTO bedRequestDTO = new BedRequestDTO();
        bedRequestDTO.setBedId(bedId);
        bedRequestDTO.setPatientId(patientId);

        Bed existingBed = new Bed();
        existingBed.setBedId(bedId);

        PatientResponseDTO patient = new PatientResponseDTO();
        patient.setId(patientId);

        when(bedRepository.findById(bedId)).thenReturn(Optional.of(existingBed));
        when(patientService.getPatientById(patientId)).thenReturn(patient);
        when(bedRepository.save(any(Bed.class))).thenReturn(existingBed);

        // Act
        Bed updatedBed = bedService.updateBed(bedRequestDTO);

        // Assert
        assertNotNull(updatedBed);
        assertEquals(patientId, updatedBed.getPatient().getId());
        assertEquals(BedType.RECEIVED, updatedBed.getStatus());
    }

    @Test
    void updateBed_shouldSetBedAsAvailableWhenNoPatient() {
        // Arrange
        String bedId = "bed1";

        BedRequestDTO bedRequestDTO = new BedRequestDTO();
        bedRequestDTO.setBedId(bedId);

        Bed existingBed = new Bed();
        existingBed.setBedId(bedId);
        existingBed.setPatient(new PatientResponseDTO());

        when(bedRepository.findById(bedId)).thenReturn(Optional.of(existingBed));
        when(bedRepository.save(any(Bed.class))).thenReturn(existingBed);

        // Act
        Bed updatedBed = bedService.updateBed(bedRequestDTO);

        // Assert
        assertNotNull(updatedBed);
        assertNull(updatedBed.getPatient());
        assertEquals(BedType.AVAILABLE, updatedBed.getStatus());
    }

    @Test
    void emptyBed_shouldMakeBedAvailableAndClearPatient() {
        // Arrange
        String bedId = "bed1";

        Bed existingBed = new Bed();
        existingBed.setBedId(bedId);
        existingBed.setPatient(new PatientResponseDTO());

        when(bedRepository.findById(bedId)).thenReturn(Optional.of(existingBed));
        when(bedRepository.save(any(Bed.class))).thenReturn(existingBed);

        // Act
        Bed emptiedBed = bedService.emptyBed(bedId);

        // Assert
        assertNotNull(emptiedBed);
        assertNull(emptiedBed.getPatient());
        assertEquals(BedType.AVAILABLE, emptiedBed.getStatus());
    }

    @Test
    void deleteBed_shouldRemoveBedFromRepository() {
        // Arrange
        String bedId = "bed1";

        when(bedRepository.existsById(bedId)).thenReturn(true);
        doNothing().when(bedRepository).deleteById(bedId);

        // Act
        bedService.deleteBed(bedId);

        // Assert
        verify(bedRepository, times(1)).deleteById(bedId);
    }

    @Test
    void deleteBed_shouldNotDeleteNonExistingBed() {
        // Arrange
        String bedId = "bed1";

        when(bedRepository.existsById(bedId)).thenReturn(false);

        // Act
        bedService.deleteBed(bedId);

        // Assert
        verify(bedRepository, times(0)).deleteById(bedId);
    }
}
