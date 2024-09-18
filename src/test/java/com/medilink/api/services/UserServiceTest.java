//package com.medilink.api.services;
//
//import com.medilink.api.models.User;
//import com.medilink.api.repositories.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//public class UserServiceTest {
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp() {
//        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void saveUser_shouldReturnSavedUser() {
//        // Arrange
//        User user = new User();
//        user.setId("1");
//        user.setName("John Doe");
//        user.setEmail("john.doe@example.com");
//        user.setPassword("password");
//
//        when(userRepository.save(user)).thenReturn(user);
//
//        // Act
//        User savedUser = userService.saveUser(user);
//
//        // Assert
//        assertEquals("1", savedUser.getId());
//        assertEquals("John Doe", savedUser.getName());
//        assertEquals("john.doe@example.com", savedUser.getEmail());
//    }
//}
