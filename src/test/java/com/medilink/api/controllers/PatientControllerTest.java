package com.medilink.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilink.api.dto.patient.PatientRequestDTO;
import com.medilink.api.dto.patient.PatientResponseDTO;
import com.medilink.api.services.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PatientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private PatientRequestDTO patientRequestDTO;
    private PatientResponseDTO patientResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();

        patientRequestDTO = new PatientRequestDTO();
        patientRequestDTO.setEmail("test@example.com");
        patientRequestDTO.setContactNumber("123456789");

        patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId("123");
        patientResponseDTO.setEmail("test@example.com");
        patientResponseDTO.setContactNumber("123456789");
    }

    @Test
    void createPatient_ShouldReturnCreatedPatient() throws Exception {
        // Arrange
        when(patientService.createPatient(any(PatientRequestDTO.class))).thenReturn(patientResponseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(patientRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.contactNumber").value("123456789"));

        verify(patientService, times(1)).createPatient(any(PatientRequestDTO.class));
    }

    @Test
    void updatePatient_ShouldReturnUpdatedPatient() throws Exception {
        // Arrange
        when(patientService.updatePatient(eq("123"), any(PatientRequestDTO.class))).thenReturn(patientResponseDTO);

        // Act & Assert
        mockMvc.perform(put("/api/patients/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(patientRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.contactNumber").value("123456789"));

        verify(patientService, times(1)).updatePatient(eq("123"), any(PatientRequestDTO.class));
    }

    @Test
    void deletePatient_ShouldReturnNoContent() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/patients/123"))
                .andExpect(status().isNoContent());

        verify(patientService, times(1)).deletePatient("123");
    }

    @Test
    void getPatient_ShouldReturnPatientById() throws Exception {
        // Arrange
        when(patientService.getPatientById("123")).thenReturn(patientResponseDTO);

        // Act & Assert
        mockMvc.perform(get("/api/patients/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.contactNumber").value("123456789"));

        verify(patientService, times(1)).getPatientById("123");
    }

    @Test
    void getAllPatients_ShouldReturnListOfPatients() throws Exception {
        // Arrange
        List<PatientResponseDTO> patientList = Arrays.asList(patientResponseDTO);
        when(patientService.getAllPatients()).thenReturn(patientList);

        // Act & Assert
        mockMvc.perform(get("/api/patients/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@example.com"))
                .andExpect(jsonPath("$[0].contactNumber").value("123456789"));

        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    void getPatientByEmail_ShouldReturnPatient() throws Exception {
        // Arrange
        when(patientService.getPatientByEmail("test@example.com")).thenReturn(patientResponseDTO);

        // Act & Assert
        mockMvc.perform(get("/api/patients/email/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.contactNumber").value("123456789"));

        verify(patientService, times(1)).getPatientByEmail("test@example.com");
    }
}
