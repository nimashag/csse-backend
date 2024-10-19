package com.medilink.api.services;

import com.medilink.api.Impl.LabTechServiceImpl;
import com.medilink.api.models.LabTech;
import com.medilink.api.repositories.LabTechRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LabTechServiceTest {

    @InjectMocks
    private LabTechServiceImpl labTechService;

    @Mock
    private LabTechRepository labTechRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private LabTech labTech;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        labTech = new LabTech();
        labTech.setId("1");
        labTech.setName("Jane Doe");
        labTech.setEmail("jane.doe@example.com");
        labTech.setPassword("password123");
    }

    @Test
    void saveLabTech_shouldReturnSavedLabTech() {
        // Arrange
        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.encode(labTech.getPassword())).thenReturn(encodedPassword);
        when(labTechRepository.save(labTech)).thenReturn(labTech);

        // Act
        LabTech savedLabTech = labTechService.saveLabTech(labTech);

        // Assert
        assertNotNull(savedLabTech);
        assertEquals("1", savedLabTech.getId());
        assertEquals(encodedPassword, savedLabTech.getPassword());
        assertEquals("Jane Doe", savedLabTech.getName());
    }

    @Test
    void getAllLabTechs_shouldReturnListOfLabTechs() {
        // Arrange
        when(labTechRepository.findAll()).thenReturn(Collections.singletonList(labTech));

        // Act
        var labTechs = labTechService.getAllLabTechs();

        // Assert
        assertNotNull(labTechs);
        assertEquals(1, labTechs.size());
        assertEquals("1", labTechs.get(0).getId());
    }

    @Test
    void getLabTechById_shouldReturnLabTech_whenLabTechExists() {
        // Arrange
        when(labTechRepository.findById("1")).thenReturn(Optional.of(labTech));

        // Act
        LabTech foundLabTech = labTechService.getLabTechById("1");

        // Assert
        assertNotNull(foundLabTech);
        assertEquals("1", foundLabTech.getId());
        assertEquals("Jane Doe", foundLabTech.getName());
    }

    @Test
    void getLabTechById_shouldThrowException_whenLabTechDoesNotExist() {
        // Arrange
        when(labTechRepository.findById("2")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            labTechService.getLabTechById("2");
        });

        assertEquals("Lab Technician not found", exception.getMessage());
    }

    @Test
    void updateLabTech_shouldReturnUpdatedLabTech_whenLabTechExists() {
        // Arrange
        LabTech updatedLabTech = new LabTech("1", "John Smith", "john.smith@example.com", "password1", "hospital1");
        when(labTechRepository.findById("1")).thenReturn(Optional.of(labTech));
        when(labTechRepository.save(updatedLabTech)).thenReturn(updatedLabTech);

        // Act
        LabTech result = labTechService.updateLabTech("1", updatedLabTech);

        // Assert
        assertNotNull(result);
        assertEquals("John Smith", result.getName());
        assertEquals("john.smith@example.com", result.getEmail());
    }

    @Test
    void updateLabTech_shouldThrowException_whenLabTechDoesNotExist() {
        // Arrange
        LabTech updatedLabTech = new LabTech("2", "John Smith", "john.smith@example.com", "password1", "hospital1");
        when(labTechRepository.findById("2")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            labTechService.updateLabTech("2", updatedLabTech);
        });

        assertEquals("Lab Technician not found", exception.getMessage());
    }

    @Test
    void deleteLabTech_shouldReturnTrue_whenLabTechExists() {
        // Arrange
        when(labTechRepository.existsById("1")).thenReturn(true);

        // Act
        boolean result = labTechService.deleteLabTech("1");

        // Assert
        assertTrue(result);
        verify(labTechRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteLabTech_shouldReturnFalse_whenLabTechDoesNotExist() {
        // Arrange
        when(labTechRepository.existsById("2")).thenReturn(false);

        // Act
        boolean result = labTechService.deleteLabTech("2");

        // Assert
        assertFalse(result);
        verify(labTechRepository, never()).deleteById("2");
    }
}
