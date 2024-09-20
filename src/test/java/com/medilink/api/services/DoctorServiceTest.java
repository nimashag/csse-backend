package com.medilink.api.services;

import com.medilink.api.Impl.DoctorServiceImpl;
import com.medilink.api.models.Doctor;
import com.medilink.api.repositories.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a Doctor object to be used in tests
        doctor = new Doctor();
        doctor.setId("1");
        doctor.setName("Dr. John Doe");
        doctor.setEmail("john.doe@example.com");
        doctor.setSpecialization("Cardiology");
    }

    @Test
    void saveDoctor_shouldReturnSavedDoctor() {
        // Arrange
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        // Act
        Doctor savedDoctor = doctorService.saveDoctor(doctor);

        // Assert
        assertNotNull(savedDoctor);
        assertEquals("1", savedDoctor.getId());
        assertEquals("Dr. John Doe", savedDoctor.getName());
        assertEquals("john.doe@example.com", savedDoctor.getEmail());
    }

    @Test
    void getAllDoctors_shouldReturnListOfDoctors() {
        // Arrange
        when(doctorRepository.findAll()).thenReturn(Collections.singletonList(doctor));

        // Act
        var doctors = doctorService.getAllDoctors();

        // Assert
        assertNotNull(doctors);
        assertEquals(1, doctors.size());
        assertEquals("1", doctors.get(0).getId());
    }

    @Test
    void getDoctorById_shouldReturnDoctor_whenDoctorExists() {
        // Arrange
        when(doctorRepository.findById("1")).thenReturn(Optional.of(doctor));

        // Act
        Doctor foundDoctor = doctorService.getDoctorById("1");

        // Assert
        assertNotNull(foundDoctor);
        assertEquals("1", foundDoctor.getId());
        assertEquals("Dr. John Doe", foundDoctor.getName());
    }

    @Test
    void getDoctorById_shouldReturnNull_whenDoctorDoesNotExist() {
        // Arrange
        when(doctorRepository.findById("2")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorService.getDoctorById("2");
        });

        assertEquals("Doctor not found", exception.getMessage());
    }

    @Test
    void updateDoctor_shouldReturnUpdatedDoctor_whenDoctorExists() {
        // Arrange
        Doctor updatedDoctor = new Doctor("1", "Dr. John Smith", "john.smith@example.com", "password1","Neurology", Arrays.asList("hospital1", "hospital2"));
        when(doctorRepository.findById("1")).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(updatedDoctor)).thenReturn(updatedDoctor);

        // Act
        Doctor result = doctorService.updateDoctor("1", updatedDoctor);

        // Assert
        assertNotNull(result);
        assertEquals("Dr. John Smith", result.getName());
        assertEquals("john.smith@example.com", result.getEmail());
    }

    @Test
    void updateDoctor_shouldReturnNull_whenDoctorDoesNotExist() {
        // Arrange
        Doctor updatedDoctor = new Doctor("2", "Dr. John Smith", "john.smith@example.com", "password1","Neurology",Arrays.asList("hospital1", "hospital2"));
        when(doctorRepository.findById("2")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorService.updateDoctor("2", updatedDoctor);
        });

        assertEquals("Doctor not found", exception.getMessage());
    }

    @Test
    void deleteDoctor_shouldReturnTrue_whenDoctorExists() {
        // Arrange
        when(doctorRepository.existsById("1")).thenReturn(true);

        // Act
        boolean result = doctorService.deleteDoctor("1");

        // Assert
        assertTrue(result);
        verify(doctorRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteDoctor_shouldReturnFalse_whenDoctorDoesNotExist() {
        // Arrange
        when(doctorRepository.existsById("2")).thenReturn(false);

        // Act
        boolean result = doctorService.deleteDoctor("2");

        // Assert
        assertFalse(result);
        verify(doctorRepository, never()).deleteById("2");
    }
}
