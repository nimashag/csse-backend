package com.medilink.api.services;

import com.medilink.api.models.User;
import com.medilink.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a User object to be used in tests
        user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
    }

    @Test
    void saveUser_shouldReturnSavedUser() {
        // Arrange
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User savedUser = userService.saveUser(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals("1", savedUser.getId());
        assertEquals("John Doe", savedUser.getName());
        assertEquals("john.doe@example.com", savedUser.getEmail());
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        // Act
        var users = userService.getAllUsers();

        // Assert
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("1", users.get(0).getId());
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.getUserById("1");

        // Assert
        assertNotNull(foundUser);
        assertEquals("1", foundUser.getId());
        assertEquals("John Doe", foundUser.getName());
    }

    @Test
    void getUserById_shouldReturnNull_whenUserDoesNotExist() {
        // Arrange
        when(userRepository.findById("2")).thenReturn(Optional.empty());

        // Act
        User foundUser = userService.getUserById("2");

        // Assert
        assertNull(foundUser);
    }

    @Test
    void updateUser_shouldReturnUpdatedUser_whenUserExists() {
        // Arrange
        User updatedUser = new User("1", "John Smith", "john.smith@example.com", "password", null);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        // Act
        User result = userService.updateUser("1", updatedUser);

        // Assert
        assertNotNull(result);
        assertEquals("John Smith", result.getName());
        assertEquals("john.smith@example.com", result.getEmail());
    }

    @Test
    void updateUser_shouldReturnNull_whenUserDoesNotExist() {
        // Arrange
        User updatedUser = new User("2", "John Smith", "john.smith@example.com", "password", null);
        when(userRepository.findById("2")).thenReturn(Optional.empty());

        // Act
        User result = userService.updateUser("2", updatedUser);

        // Assert
        assertNull(result);
    }

    @Test
    void deleteUser_shouldReturnTrue_whenUserExists() {
        // Arrange
        when(userRepository.existsById("1")).thenReturn(true);

        // Act
        boolean result = userService.deleteUser("1");

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteUser_shouldReturnFalse_whenUserDoesNotExist() {
        // Arrange
        when(userRepository.existsById("2")).thenReturn(false);

        // Act
        boolean result = userService.deleteUser("2");

        // Assert
        assertFalse(result);
        verify(userRepository, never()).deleteById("2");
    }
}
