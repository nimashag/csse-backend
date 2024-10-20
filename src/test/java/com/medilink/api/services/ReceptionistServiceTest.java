package com.medilink.api.services;

import com.medilink.api.models.Hospital;
import com.medilink.api.models.Receptionist;
import com.medilink.api.repositories.ReceptionistRepository;
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

public class ReceptionistServiceTest {

    @InjectMocks
    private ReceptionistService receptionistService;

    @Mock
    private ReceptionistRepository receptionistRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveReceptionist_shouldReturnSavedReceptionist() {
        // Arrange
        Receptionist receptionist = new Receptionist();
        receptionist.setId("1");
        receptionist.setName("John Doe");

        when(receptionistRepository.save(receptionist)).thenReturn(receptionist);

        // Act
        Receptionist savedReceptionist = receptionistService.saveReceptionist(receptionist);

        // Assert
        assertNotNull(savedReceptionist);
        assertEquals("1", savedReceptionist.getId());
        assertEquals("John Doe", savedReceptionist.getName());
    }

    @Test
    void getAllReceptionists_shouldReturnListOfReceptionists() {
        // Arrange
        Receptionist receptionist1 = new Receptionist("1", "John Doe", "john@example.com", "123456789",new Hospital());
        Receptionist receptionist2 = new Receptionist("2", "Jane Doe", "jane@example.com", "987654321",new Hospital());
        List<Receptionist> receptionists = Arrays.asList(receptionist1, receptionist2);

        when(receptionistRepository.findAll()).thenReturn(receptionists);

        // Act
        List<Receptionist> result = receptionistService.getAllReceptionists();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
    }

    @Test
    void getReceptionist_shouldReturnReceptionist_whenReceptionistExists() {
        // Arrange
        Receptionist receptionist = new Receptionist("1", "John Doe", "john@example.com", "123456789",new Hospital());

        when(receptionistRepository.findById("1")).thenReturn(Optional.of(receptionist));

        // Act
        Receptionist result = receptionistService.getReceptionist("1");

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void getReceptionist_shouldReturnNull_whenReceptionistDoesNotExist() {
        // Arrange
        when(receptionistRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        Receptionist result = receptionistService.getReceptionist("1");

        // Assert
        assertNull(result);
    }

    @Test
    void updateReceptionist_shouldReturnUpdatedReceptionist_whenReceptionistExists() {
        // Arrange
        Receptionist existingReceptionist = new Receptionist("1", "John Doe", "john@example.com", "123456789",new Hospital());
        Receptionist updatedReceptionist = new Receptionist("1", "John Smith", "johnsmith@example.com", "123456789",new Hospital());

        when(receptionistRepository.findById("1")).thenReturn(Optional.of(existingReceptionist));
        when(receptionistRepository.save(updatedReceptionist)).thenReturn(updatedReceptionist);

        // Act
        Receptionist result = receptionistService.updateReceptionist("1", updatedReceptionist);

        // Assert
        assertNotNull(result);
        assertEquals("John Smith", result.getName());
    }

    @Test
    void updateReceptionist_shouldReturnNull_whenReceptionistDoesNotExist() {
        // Arrange
        Receptionist updatedReceptionist = new Receptionist("1", "John Smith", "johnsmith@example.com", "123456789",new Hospital());

        when(receptionistRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        Receptionist result = receptionistService.updateReceptionist("1", updatedReceptionist);

        // Assert
        assertNull(result);
    }

    @Test
    void deleteReceptionist_shouldReturnTrue_whenReceptionistExists() {
        // Arrange
        String receptionistId = "1";
        when(receptionistRepository.existsById(receptionistId)).thenReturn(true);

        // Act
        boolean result = receptionistService.deleteReceptionist(receptionistId);

        // Assert
        assertTrue(result);
        verify(receptionistRepository, times(1)).deleteById(receptionistId);
    }

    @Test
    void deleteReceptionist_shouldReturnFalse_whenReceptionistDoesNotExist() {
        // Arrange
        String receptionistId = "1";
        when(receptionistRepository.existsById(receptionistId)).thenReturn(false);

        // Act
        boolean result = receptionistService.deleteReceptionist(receptionistId);

        // Assert
        assertFalse(result);
        verify(receptionistRepository, never()).deleteById(receptionistId);
    }
}
