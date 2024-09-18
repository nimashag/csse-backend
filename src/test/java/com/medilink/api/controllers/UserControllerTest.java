package com.medilink.api.controllers;

import com.medilink.api.dto.user.UserRequestDTO;
import com.medilink.api.dto.user.UserResponseDTO;
import com.medilink.api.enums.UserType;
import com.medilink.api.models.User;
import com.medilink.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        // Arrange
        UserRequestDTO requestDto = new UserRequestDTO();
        requestDto.setName("John Doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setPassword("password");
        requestDto.setUserType(UserType.DOCTOR); // Set userType

        // Create the user object to be returned by the modelMapper
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setUserType(UserType.DOCTOR);
        user.setId("1"); // Set the ID

        // Create the UserResponseDTO object to be returned by the modelMapper
        UserResponseDTO responseDto = new UserResponseDTO();
        responseDto.setName("John Doe");
        responseDto.setEmail("john.doe@example.com");

        // Mock the modelMapper to return the user when mapping the request DTO
        when(modelMapper.map(requestDto, User.class)).thenReturn(user);

        // Mock the saveUser method to return the same user
        when(userService.saveUser(user)).thenReturn(user);

        // Mock the modelMapper to return the UserResponseDTO when mapping the saved user
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<UserResponseDTO> response = userController.createUser(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Expect 201 Created
        UserResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody); // Check if response body is not null
        assertEquals("John Doe", responseBody.getName());
        assertEquals("john.doe@example.com", responseBody.getEmail());
    }



    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        // Arrange
        User user1 = new User("1", "John Doe", "john.doe@example.com", "password", UserType.DOCTOR);
        User user2 = new User("2", "Jane Doe", "jane.doe@example.com", "password", UserType.PATIENT);
        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        UserResponseDTO responseDto1 = new UserResponseDTO();
        responseDto1.setName("John Doe");
        responseDto1.setEmail("john.doe@example.com");

        UserResponseDTO responseDto2 = new UserResponseDTO();
        responseDto2.setName("Jane Doe");
        responseDto2.setEmail("jane.doe@example.com");

        // Mock the modelMapper for both users
        when(modelMapper.map(user1, UserResponseDTO.class)).thenReturn(responseDto1);
        when(modelMapper.map(user2, UserResponseDTO.class)).thenReturn(responseDto2);

        // Act
        ResponseEntity<List<UserResponseDTO>> response = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<UserResponseDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertEquals("John Doe", responseBody.get(0).getName());  // No longer throws NullPointerException
        assertEquals("Jane Doe", responseBody.get(1).getName());
    }


    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        // Arrange
        User user = new User("1", "John Doe", "john.doe@example.com", "password", UserType.DOCTOR);

        // Create the UserResponseDTO that should be returned
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setName("John Doe");
        userResponseDTO.setEmail("john.doe@example.com");

        // Mock the userService to return the user
        when(userService.getUserById("1")).thenReturn(user);

        // Mock the modelMapper to return the UserResponseDTO when mapping the user
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);

        // Act
        ResponseEntity<UserResponseDTO> response = userController.getUserById("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody); // Check if response body is not null
        assertEquals("John Doe", responseBody.getName());
    }


    @Test
    void getUserById_shouldReturnNotFound_whenUserDoesNotExist() {
        // Arrange
        when(userService.getUserById("1")).thenReturn(null);

        // Act
        ResponseEntity<UserResponseDTO> response = userController.getUserById("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void updateUser_shouldReturnUpdatedUser_whenUserExists() {
        // Arrange
        UserRequestDTO requestDto = new UserRequestDTO("John Doe", "john.doe@example.com", "password", UserType.DOCTOR);
        User existingUser = new User("1", "John Doe", "john.doe@example.com", "password", UserType.DOCTOR);
        User updatedUser = new User("1", "John Smith", "john.smith@example.com", "password", UserType.DOCTOR);

        // Mock the userService to return the updated user
        when(userService.updateUser("1", modelMapper.map(requestDto, User.class))).thenReturn(updatedUser);

        // Create the UserResponseDTO to be returned after mapping
        UserResponseDTO responseDto = new UserResponseDTO();
        responseDto.setName("John Smith");
        responseDto.setEmail("john.smith@example.com");

        // Mock the modelMapper to return the responseDto when mapping the updated user
        when(modelMapper.map(updatedUser, UserResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<UserResponseDTO> response = userController.updateUser("1", requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody); // Check if response body is not null
        assertEquals("John Smith", responseBody.getName());
        assertEquals("john.smith@example.com", responseBody.getEmail());
    }


    @Test
    void updateUser_shouldReturnNotFound_whenUserDoesNotExist() {
        // Arrange
        UserRequestDTO requestDto = new UserRequestDTO("John Doe", "john.doe@example.com", "password", UserType.DOCTOR);
        when(userService.updateUser("1", modelMapper.map(requestDto, User.class))).thenReturn(null);

        // Act
        ResponseEntity<UserResponseDTO> response = userController.updateUser("1", requestDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

//    @Test
//    void deleteUser_shouldReturnNoContent_whenUserDeleted() {
//        // Arrange
//        when(userService.deleteUser("1")).thenReturn(true);
//
//        // Act
//        ResponseEntity<Void> response = userController.deleteUser("1");
//
//        // Assert
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//    }
//
//    @Test
//    void deleteUser_shouldReturnNotFound_whenUserDoesNotExist() {
//        // Arrange
//        when(userService.deleteUser("1")).thenReturn(false);
//
//        // Act
//        ResponseEntity<Void> response = userController.deleteUser("1");
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }
}
