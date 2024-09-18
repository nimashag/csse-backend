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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        // Arrange
        UserRequestDTO requestDto = new UserRequestDTO();
        requestDto.setName("John Doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setPassword("password");
        requestDto.setUserType(UserType.DOCTOR); // Set userType

        User user = modelMapper.map(requestDto, User.class);
        user.setId("1");

        when(userService.saveUser(user)).thenReturn(user);

        // Act
        ResponseEntity<UserResponseDTO> response = userController.createUser(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        UserResponseDTO responseBody = response.getBody();
        assert responseBody != null; // Check if response body is not null
        assertEquals("John Doe", responseBody.getName());
        assertEquals("john.doe@example.com", responseBody.getEmail());
    }
}
