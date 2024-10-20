package com.medilink.api.services;

import com.medilink.api.models.Receptionist;
import com.medilink.api.repositories.ReceptionistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReceptionistServiceTest {

    @Mock
    private ReceptionistRepository receptionistRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ReceptionistService receptionistService;

    private Receptionist receptionist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        receptionist = new Receptionist("1", "John Doe", "john@example.com", "password", "hospital1");
    }

    @Test
    void saveReceptionist_shouldSaveReceptionistWithEncodedPassword() {
        // Arrange
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(receptionistRepository.save(receptionist)).thenReturn(receptionist);

        // Act
        Receptionist savedReceptionist = receptionistService.saveReceptionist(receptionist);

        // Assert
        assertNotNull(savedReceptionist);
        assertEquals(encodedPassword, savedReceptionist.getPassword());
        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(receptionistRepository, times(1)).save(receptionist);
    }

    @Test
    void getAllReceptionists_shouldReturnListOfReceptionists() {
        // Arrange
        List<Receptionist> receptionistList = new ArrayList<>();
        receptionistList.add(receptionist);
        when(receptionistRepository.findAll()).thenReturn(receptionistList);

        // Act
        List<Receptionist> result = receptionistService.getAllReceptionists();

        // Assert
        assertEquals(1, result.size());
        assertEquals(receptionistList, result);
        verify(receptionistRepository, times(1)).findAll();
    }

    @Test
    void getReceptionist_shouldReturnReceptionistById_whenExists() {
        // Arrange
        String id = "1";
        when(receptionistRepository.findById(id)).thenReturn(Optional.of(receptionist));

        // Act
        Receptionist result = receptionistService.getReceptionist(id);

        // Assert
        assertNotNull(result);
        assertEquals(receptionist, result);
        verify(receptionistRepository, times(1)).findById(id);
    }

    @Test
    void getReceptionist_shouldReturnNull_whenReceptionistDoesNotExist() {
        // Arrange
        String id = "2";
        when(receptionistRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Receptionist result = receptionistService.getReceptionist(id);

        // Assert
        assertNull(result);
        verify(receptionistRepository, times(1)).findById(id);
    }

    @Test
    void updateReceptionist_shouldUpdateReceptionistWithEncodedPassword_whenPasswordIsProvided() {
        // Arrange
        String newPassword = "newPassword";
        String encodedNewPassword = "encodedNewPassword";
        receptionist.setPassword(newPassword);
        when(receptionistRepository.findById(receptionist.getId())).thenReturn(Optional.of(receptionist));
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);
        when(receptionistRepository.save(receptionist)).thenReturn(receptionist);

        // Act
        Receptionist updatedReceptionist = receptionistService.updateReceptionist(receptionist.getId(), receptionist);

        // Assert
        assertNotNull(updatedReceptionist);
        assertEquals(encodedNewPassword, updatedReceptionist.getPassword());
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(receptionistRepository, times(1)).save(receptionist);
    }


    @Test
    void deleteReceptionist_shouldReturnTrue_whenReceptionistExists() {
        // Arrange
        String id = "1";
        when(receptionistRepository.existsById(id)).thenReturn(true);

        // Act
        boolean result = receptionistService.deleteReceptionist(id);

        // Assert
        assertTrue(result);
        verify(receptionistRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteReceptionist_shouldReturnFalse_whenReceptionistDoesNotExist() {
        // Arrange
        String id = "2";
        when(receptionistRepository.existsById(id)).thenReturn(false);

        // Act
        boolean result = receptionistService.deleteReceptionist(id);

        // Assert
        assertFalse(result);
        verify(receptionistRepository, times(0)).deleteById(anyString());
    }

    @Test
    void authenticateReceptionist_shouldReturnReceptionist_whenCredentialsAreValid() {
        // Arrange
        String email = "john@example.com";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";
        receptionist.setPassword(encodedPassword);
        when(receptionistRepository.findByEmail(email)).thenReturn(Optional.of(receptionist));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // Act
        Receptionist authenticatedReceptionist = receptionistService.authenticateReceptionist(email, rawPassword);

        // Assert
        assertNotNull(authenticatedReceptionist);
        assertEquals(receptionist, authenticatedReceptionist);
        verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
    }

    @Test
    void authenticateReceptionist_shouldReturnNull_whenCredentialsAreInvalid() {
        // Arrange
        String email = "john@example.com";
        String rawPassword = "wrongPassword";
        when(receptionistRepository.findByEmail(email)).thenReturn(Optional.of(receptionist));
        when(passwordEncoder.matches(rawPassword, receptionist.getPassword())).thenReturn(false);

        // Act
        Receptionist authenticatedReceptionist = receptionistService.authenticateReceptionist(email, rawPassword);

        // Assert
        assertNull(authenticatedReceptionist);
        verify(passwordEncoder, times(1)).matches(rawPassword, receptionist.getPassword());
    }
}
