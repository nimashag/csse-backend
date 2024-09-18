package com.medilink.api.controllers;

import com.medilink.api.dto.user.UserRequestDTO;
import com.medilink.api.dto.user.UserResponseDTO;
import com.medilink.api.models.User;
import com.medilink.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        User user = modelMapper.map(userRequestDTO, User.class);
        User savedUser = userService.saveUser(user);
        UserResponseDTO userResponseDTO = modelMapper.map(savedUser, UserResponseDTO.class);
        return ResponseEntity.ok(userResponseDTO);
    }
}
