package com.medilink.api.services;

import com.medilink.api.models.Ward;
import com.medilink.api.models.WardHead;
import com.medilink.api.repositories.WardHeadRepository;
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

public class WardHeadServiceTest {

    @InjectMocks
    private WardHeadService wardHeadService;

    @Mock
    private WardHeadRepository wardHeadRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveWardHead_shouldReturnSavedWardHead() {
        // Arrange
        Ward ward = new Ward();
        WardHead wardHead = new WardHead("1", "Alice Smith", "alice@example.com", "password", ward);

        when(wardHeadRepository.save(wardHead)).thenReturn(wardHead);

        // Act
        WardHead savedWardHead = wardHeadService.saveWardHead(wardHead);

        // Assert
        assertNotNull(savedWardHead);
        assertEquals("1", savedWardHead.getId());
        assertEquals("Alice Smith", savedWardHead.getName());
        assertEquals(ward, savedWardHead.getWard());
    }

    @Test
    void getAllWardHead_shouldReturnListOfWardHeads() {
        // Arrange
        Ward ward = new Ward();
        WardHead wardHead1 = new WardHead("1", "Alice Smith", "alice@example.com", "password", ward);
        WardHead wardHead2 = new WardHead("2", "Bob Johnson", "bob@example.com", "password", ward);
        List<WardHead> wardHeads = Arrays.asList(wardHead1, wardHead2);

        when(wardHeadRepository.findAll()).thenReturn(wardHeads);

        // Act
        List<WardHead> result = wardHeadService.getAllWardHead();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice Smith", result.get(0).getName());
        assertEquals("Bob Johnson", result.get(1).getName());
    }

    @Test
    void getWardHead_shouldReturnWardHead_whenWardHeadExists() {
        // Arrange
        Ward ward = new Ward();
        WardHead wardHead = new WardHead("1", "Alice Smith", "alice@example.com", "password", ward);

        when(wardHeadRepository.findById("1")).thenReturn(Optional.of(wardHead));

        // Act
        WardHead result = wardHeadService.getWardHead("1");

        // Assert
        assertNotNull(result);
        assertEquals("Alice Smith", result.getName());
        assertEquals(ward, result.getWard());
    }

    @Test
    void getWardHead_shouldReturnNull_whenWardHeadDoesNotExist() {
        // Arrange
        when(wardHeadRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        WardHead result = wardHeadService.getWardHead("1");

        // Assert
        assertNull(result);
    }

    @Test
    void updateWardHead_shouldReturnUpdatedWardHead_whenWardHeadExists() {
        // Arrange
        Ward ward = new Ward();
        WardHead existingWardHead = new WardHead("1", "Alice Smith", "alice@example.com", "password", ward);
        WardHead updatedWardHead = new WardHead("1", "Alice Johnson", "alicej@example.com", "password", ward);

        when(wardHeadRepository.findById("1")).thenReturn(Optional.of(existingWardHead));
        when(wardHeadRepository.save(updatedWardHead)).thenReturn(updatedWardHead);

        // Act
        WardHead result = wardHeadService.updateWardHead("1", updatedWardHead);

        // Assert
        assertNotNull(result);
        assertEquals("Alice Johnson", result.getName());
    }

    @Test
    void updateWardHead_shouldReturnNull_whenWardHeadDoesNotExist() {
        // Arrange
        Ward ward = new Ward();
        WardHead updatedWardHead = new WardHead("1", "Alice Johnson", "alicej@example.com", "password", ward);

        when(wardHeadRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        WardHead result = wardHeadService.updateWardHead("1", updatedWardHead);

        // Assert
        assertNull(result);
    }

    @Test
    void deleteWardHead_shouldReturnTrue_whenWardHeadExists() {
        // Arrange
        String wardHeadId = "1";
        when(wardHeadRepository.existsById(wardHeadId)).thenReturn(true);

        // Act
        boolean result = wardHeadService.deleteWardHead(wardHeadId);

        // Assert
        assertTrue(result);
        verify(wardHeadRepository, times(1)).deleteById(wardHeadId);
    }

    @Test
    void deleteWardHead_shouldReturnFalse_whenWardHeadDoesNotExist() {
        // Arrange
        String wardHeadId = "1";
        when(wardHeadRepository.existsById(wardHeadId)).thenReturn(false);

        // Act
        boolean result = wardHeadService.deleteWardHead(wardHeadId);

        // Assert
        assertFalse(result);
        verify(wardHeadRepository, never()).deleteById(wardHeadId);
    }
}
