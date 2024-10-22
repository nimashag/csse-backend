package com.medilink.api.services;

import com.medilink.api.dto.patient.PatientRequestDTO;
import com.medilink.api.dto.patient.PatientResponseDTO;
import com.medilink.api.models.Patient;
import com.medilink.api.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ModelMapper modelMapper;

    private Patient patient;
    private PatientRequestDTO patientRequestDTO;
    private PatientResponseDTO patientResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        patient = new Patient();
        patient.setId("123");
        patient.setEmail("test@example.com");

        patientRequestDTO = new PatientRequestDTO();
        patientRequestDTO.setEmail("test@example.com");

        patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setEmail("test@example.com");
    }

    @Test
    void createPatient_ShouldReturnPatientResponseDTO() {
        // Arrange
        when(modelMapper.map(patientRequestDTO, Patient.class)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(modelMapper.map(patient, PatientResponseDTO.class)).thenReturn(patientResponseDTO);

        // Act
        PatientResponseDTO result = patientService.createPatient(patientRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void updatePatient_ShouldUpdateAndReturnPatientResponseDTO() {
        // Arrange
        when(patientRepository.findById("123")).thenReturn(Optional.of(patient)); // Return a valid patient

        // Use doAnswer to simulate in-place mapping
        doAnswer(invocation -> {
            PatientRequestDTO request = invocation.getArgument(0);
            Patient target = invocation.getArgument(1);
            // Simulate the mapping (copy the relevant fields)
            target.setEmail(request.getEmail()); // Example field
            return null; // map() doesn't return a value here, it modifies the object
        }).when(modelMapper).map(any(PatientRequestDTO.class), eq(patient));

        when(patientRepository.save(patient)).thenReturn(patient);                // Save patient
        when(modelMapper.map(patient, PatientResponseDTO.class)).thenReturn(patientResponseDTO); // Map to response DTO

        // Act
        PatientResponseDTO result = patientService.updatePatient("123", patientRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(patientRepository, times(1)).save(patient); // Verify save is called once
    }




    @Test
    void getPatientByEmail_ShouldReturnPatientResponseDTO() {
        // Arrange
        when(patientRepository.findByEmail("test@example.com")).thenReturn(Optional.of(patient));
        when(modelMapper.map(patient, PatientResponseDTO.class)).thenReturn(patientResponseDTO);

        // Act
        PatientResponseDTO result = patientService.getPatientByEmail("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void deletePatient_ShouldDeletePatient() {
        // Arrange
        doNothing().when(patientRepository).deleteById("123");

        // Act
        patientService.deletePatient("123");

        // Assert
        verify(patientRepository, times(1)).deleteById("123");
    }

    @Test
    void getPatientById_ShouldReturnPatientResponseDTO() {
        // Arrange
        when(patientRepository.findById("123")).thenReturn(Optional.of(patient));
        when(modelMapper.map(patient, PatientResponseDTO.class)).thenReturn(patientResponseDTO);

        // Act
        PatientResponseDTO result = patientService.getPatientById("123");

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void getAllPatients_ShouldReturnListOfPatientResponseDTO() {
        // Arrange
        Patient anotherPatient = new Patient();
        anotherPatient.setId("456");
        anotherPatient.setEmail("test2@example.com");

        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient, anotherPatient));
        when(modelMapper.map(any(Patient.class), eq(PatientResponseDTO.class)))
                .thenReturn(patientResponseDTO);

        // Act
        List<PatientResponseDTO> result = patientService.getAllPatients();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }
}
