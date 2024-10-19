package com.medilink.api.controllers;

import com.medilink.api.dto.doctor.DoctorRequestDTO;
import com.medilink.api.dto.doctor.DoctorResponseDTO;
import com.medilink.api.models.Doctor;
import com.medilink.api.services.DoctorService;
import com.medilink.api.services.EmailService;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DoctorControllerTest {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorService doctorService;

    @Mock
    private EmailService emailService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDoctor_shouldReturnCreatedDoctor() {
        // Arrange
        DoctorRequestDTO requestDto = new DoctorRequestDTO();
        requestDto.setName("Dr. John Doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setSpecialization("Cardiology");
        requestDto.setPassword("password123");
        requestDto.setWorkingHospitals(Arrays.asList("hospital1", "hospital2"));

        Doctor doctor = new Doctor();
        doctor.setName("Dr. John Doe");
        doctor.setEmail("john.doe@example.com");
        doctor.setSpecialization("Cardiology");
        doctor.setPassword("password123");

        DoctorResponseDTO responseDto = new DoctorResponseDTO();
        responseDto.setName("Dr. John Doe");
        responseDto.setEmail("john.doe@example.com");

        when(modelMapper.map(requestDto, Doctor.class)).thenReturn(doctor);
        when(doctorService.saveDoctor(doctor)).thenReturn(doctor);
        when(modelMapper.map(doctor, DoctorResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<DoctorResponseDTO> response = doctorController.createDoctor(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        DoctorResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Dr. John Doe", responseBody.getName());
        assertEquals("john.doe@example.com", responseBody.getEmail());
    }

    @Test
    void getAllDoctors_shouldReturnListOfDoctors() {
        // Arrange
        Doctor doctor1 = new Doctor("1", "Dr. John Doe", "john.doe@example.com", "password1", "Cardiology", Arrays.asList("hospital1", "hospital2"));
        Doctor doctor2 = new Doctor("2", "Dr. Jane Doe", "jane.doe@example.com", "password2", "Dermatology", Arrays.asList("hospital1", "hospital2"));
        List<Doctor> doctors = Arrays.asList(doctor1, doctor2);

        when(doctorService.getAllDoctors()).thenReturn(doctors);

        DoctorResponseDTO responseDto1 = new DoctorResponseDTO();
        responseDto1.setName("Dr. John Doe");
        responseDto1.setEmail("john.doe@example.com");

        DoctorResponseDTO responseDto2 = new DoctorResponseDTO();
        responseDto2.setName("Dr. Jane Doe");
        responseDto2.setEmail("jane.doe@example.com");

        when(modelMapper.map(doctor1, DoctorResponseDTO.class)).thenReturn(responseDto1);
        when(modelMapper.map(doctor2, DoctorResponseDTO.class)).thenReturn(responseDto2);

        // Act
        ResponseEntity<List<DoctorResponseDTO>> response = doctorController.getAllDoctors();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<DoctorResponseDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertEquals("Dr. John Doe", responseBody.get(0).getName());
        assertEquals("Dr. Jane Doe", responseBody.get(1).getName());
    }

    @Test
    void getDoctorById_shouldReturnDoctor_whenDoctorExists() {
        // Arrange
        Doctor doctor = new Doctor("1", "Dr. John Doe", "john.doe@example.com", "password1", "Cardiology", Arrays.asList("hospital1", "hospital2"));

        DoctorResponseDTO responseDto = new DoctorResponseDTO();
        responseDto.setName("Dr. John Doe");
        responseDto.setEmail("john.doe@example.com");

        when(doctorService.getDoctorById("1")).thenReturn(doctor);
        when(modelMapper.map(doctor, DoctorResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<DoctorResponseDTO> response = doctorController.getDoctorById("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        DoctorResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Dr. John Doe", responseBody.getName());
    }

    @Test
    void getDoctorById_shouldReturnNotFound_whenDoctorDoesNotExist() {
        // Arrange
        when(doctorService.getDoctorById("1")).thenReturn(null);

        // Act
        ResponseEntity<DoctorResponseDTO> response = doctorController.getDoctorById("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateDoctor_shouldReturnUpdatedDoctor_whenDoctorExists() {
        // Arrange
        DoctorRequestDTO requestDto = new DoctorRequestDTO();
        requestDto.setName("Dr. John Smith");
        requestDto.setEmail("john.smith@example.com");
        requestDto.setSpecialization("Neurology");

        Doctor existingDoctor = new Doctor("1", "Dr. John Doe", "john.doe@example.com", "password1", "Cardiology", Arrays.asList("hospital1", "hospital2"));
        Doctor updatedDoctor = new Doctor("1", "Dr. John Smith", "john.smith@example.com", "password2", "Neurology", Arrays.asList("hospital1", "hospital2"));

        when(doctorService.updateDoctor("1", modelMapper.map(requestDto, Doctor.class))).thenReturn(updatedDoctor);

        DoctorResponseDTO responseDto = new DoctorResponseDTO();
        responseDto.setName("Dr. John Smith");
        responseDto.setEmail("john.smith@example.com");

        when(modelMapper.map(updatedDoctor, DoctorResponseDTO.class)).thenReturn(responseDto);

        // Act
        ResponseEntity<DoctorResponseDTO> response = doctorController.updateDoctor("1", requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        DoctorResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Dr. John Smith", responseBody.getName());
        assertEquals("john.smith@example.com", responseBody.getEmail());
    }

    @Test
    void updateDoctor_shouldReturnNotFound_whenDoctorDoesNotExist() {
        // Arrange
        DoctorRequestDTO requestDto = new DoctorRequestDTO();
        requestDto.setName("Dr. John Doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setSpecialization("Cardiology");

        when(doctorService.updateDoctor("1", modelMapper.map(requestDto, Doctor.class))).thenReturn(null);

        // Act
        ResponseEntity<DoctorResponseDTO> response = doctorController.updateDoctor("1", requestDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteDoctor_shouldReturnNoContent_whenDoctorDeleted() {
        // Arrange
        when(doctorService.deleteDoctor("1")).thenReturn(true);

        // Act
        ResponseEntity<Void> response = doctorController.deleteDoctor("1");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteDoctor_shouldReturnNotFound_whenDoctorDoesNotExist() {
        // Arrange
        when(doctorService.deleteDoctor("1")).thenReturn(false);

        // Act
        ResponseEntity<Void> response = doctorController.deleteDoctor("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getDoctorsByHospital_shouldReturnDoctors() {
        // Arrange
        Doctor doctor1 = new Doctor("1", "Dr. John Doe", "john.doe@example.com", "password1", "Cardiology", Arrays.asList("hospital1", "hospital2"));
        Doctor doctor2 = new Doctor("2", "Dr. Jane Doe", "jane.doe@example.com", "password2", "Dermatology", Arrays.asList("hospital1", "hospital2"));
        List<Doctor> doctors = Arrays.asList(doctor1, doctor2);

        when(doctorService.getDoctorsByHospitalId("hospital1")).thenReturn(doctors);

        DoctorResponseDTO responseDto1 = new DoctorResponseDTO();
        responseDto1.setName("Dr. John Doe");
        responseDto1.setEmail("john.doe@example.com");

        DoctorResponseDTO responseDto2 = new DoctorResponseDTO();
        responseDto2.setName("Dr. Jane Doe");
        responseDto2.setEmail("jane.doe@example.com");

        when(modelMapper.map(doctor1, DoctorResponseDTO.class)).thenReturn(responseDto1);
        when(modelMapper.map(doctor2, DoctorResponseDTO.class)).thenReturn(responseDto2);

        // Act
        ResponseEntity<List<DoctorResponseDTO>> response = doctorController.getDoctorsByHospital("hospital1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<DoctorResponseDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertEquals("Dr. John Doe", responseBody.get(0).getName());
        assertEquals("Dr. Jane Doe", responseBody.get(1).getName());
    }
}
