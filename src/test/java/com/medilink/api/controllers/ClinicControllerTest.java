package com.medilink.api.controllers;

import com.medilink.api.dto.clinic.ClinicRequestDTO;
import com.medilink.api.dto.clinic.ClinicResponseDTO;
import com.medilink.api.models.Clinic;
import com.medilink.api.models.Doctor;
import com.medilink.api.models.Hospital;
import com.medilink.api.models.Patient;
import com.medilink.api.services.ClinicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ClinicControllerTest {

    @InjectMocks
    private ClinicController clinicController;

    @Mock
    private ClinicService clinicService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClinic_shouldReturnCreatedClinic() {
        // Arrange
        ClinicRequestDTO clinicRequestDTO = new ClinicRequestDTO();
        clinicRequestDTO.setClinicName("General Clinic");
        clinicRequestDTO.setDoctorId("doctor123");
        clinicRequestDTO.setHospitalId("hospital123");

        Clinic savedClinic = new Clinic("clinic123", "General Clinic", new Doctor(), new Hospital(), new ArrayList<>());
        ClinicResponseDTO clinicResponseDTO = new ClinicResponseDTO();
        clinicResponseDTO.setId("clinic123");
        clinicResponseDTO.setClinicName("General Clinic");

        when(clinicService.saveClinic(clinicRequestDTO)).thenReturn(savedClinic);
        when(modelMapper.map(savedClinic, ClinicResponseDTO.class)).thenReturn(clinicResponseDTO);

        // Act
        ResponseEntity<ClinicResponseDTO> response = clinicController.createClinic(clinicRequestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ClinicResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("clinic123", responseBody.getId());
        assertEquals("General Clinic", responseBody.getClinicName());
    }

    @Test
    void addPatientToClinic_shouldReturnUpdatedClinic() {
        // Arrange
        String clinicId = "clinic123";
        String patientId = "patient123";
        Clinic updatedClinic = new Clinic(clinicId, "General Clinic", new Doctor(), new Hospital(), new ArrayList<>());
        updatedClinic.getPatients().add(new Patient());

        ClinicResponseDTO clinicResponseDTO = new ClinicResponseDTO();
        clinicResponseDTO.setId(clinicId);
        clinicResponseDTO.setClinicName("General Clinic");
        clinicResponseDTO.setPatients(updatedClinic.getPatients());

        when(clinicService.addPatientToClinic(clinicId, patientId)).thenReturn(updatedClinic);
        when(modelMapper.map(updatedClinic, ClinicResponseDTO.class)).thenReturn(clinicResponseDTO);

        // Act
        ResponseEntity<ClinicResponseDTO> response = clinicController.addPatientToClinic(clinicId, patientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ClinicResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("clinic123", responseBody.getId());
        assertEquals(1, responseBody.getPatients().size()); // Confirm patient was added
    }

    @Test
    void getAllClinics_shouldReturnListOfClinics() {
        // Arrange
        Clinic clinic1 = new Clinic("clinic123", "Clinic A", new Doctor(), new Hospital(), new ArrayList<>());
        Clinic clinic2 = new Clinic("clinic124", "Clinic B", new Doctor(), new Hospital(), new ArrayList<>());
        List<Clinic> clinics = Arrays.asList(clinic1, clinic2);

        ClinicResponseDTO responseDTO1 = new ClinicResponseDTO();
        responseDTO1.setId("clinic123");
        responseDTO1.setClinicName("Clinic A");

        ClinicResponseDTO responseDTO2 = new ClinicResponseDTO();
        responseDTO2.setId("clinic124");
        responseDTO2.setClinicName("Clinic B");

        when(clinicService.getClinics()).thenReturn(clinics);
        when(modelMapper.map(clinic1, ClinicResponseDTO.class)).thenReturn(responseDTO1);
        when(modelMapper.map(clinic2, ClinicResponseDTO.class)).thenReturn(responseDTO2);

        // Act
        ResponseEntity<List<ClinicResponseDTO>> response = clinicController.getAllClinics();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ClinicResponseDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertEquals("clinic123", responseBody.get(0).getId());
        assertEquals("clinic124", responseBody.get(1).getId());
    }

    @Test
    void getClinics_shouldReturnClinic_whenClinicExists() {
        // Arrange
        String clinicId = "clinic123";
        Clinic clinic = new Clinic(clinicId, "Clinic A", new Doctor(), new Hospital(), new ArrayList<>());

        ClinicResponseDTO responseDTO = new ClinicResponseDTO();
        responseDTO.setId(clinicId);
        responseDTO.setClinicName("Clinic A");

        when(clinicService.getOneClinic(clinicId)).thenReturn(clinic);
        when(modelMapper.map(clinic, ClinicResponseDTO.class)).thenReturn(responseDTO);

        // Act
        ResponseEntity<ClinicResponseDTO> response = clinicController.getClinics(clinicId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ClinicResponseDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(clinicId, responseBody.getId());
        assertEquals("Clinic A", responseBody.getClinicName());
    }

    @Test
    void getClinics_shouldReturnNotFound_whenClinicDoesNotExist() {
        // Arrange
        when(clinicService.getOneClinic("clinic123")).thenReturn(null);

        // Act
        ResponseEntity<ClinicResponseDTO> response = clinicController.getClinics("clinic123");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getClinicsByPatientId_shouldReturnClinics() {
        // Arrange
        String patientId = "patient123";
        Clinic clinic1 = new Clinic("clinic123", "Clinic A", new Doctor(), new Hospital(), new ArrayList<>());
        Clinic clinic2 = new Clinic("clinic124", "Clinic B", new Doctor(), new Hospital(), new ArrayList<>());
        List<Clinic> clinics = Arrays.asList(clinic1, clinic2);

        ClinicResponseDTO responseDTO1 = new ClinicResponseDTO();
        responseDTO1.setId("clinic123");
        responseDTO1.setClinicName("Clinic A");

        ClinicResponseDTO responseDTO2 = new ClinicResponseDTO();
        responseDTO2.setId("clinic124");
        responseDTO2.setClinicName("Clinic B");

        when(clinicService.getClinicsByPatientId(patientId)).thenReturn(clinics);
        when(modelMapper.map(clinic1, ClinicResponseDTO.class)).thenReturn(responseDTO1);
        when(modelMapper.map(clinic2, ClinicResponseDTO.class)).thenReturn(responseDTO2);

        // Act
        ResponseEntity<List<ClinicResponseDTO>> response = clinicController.getClinicsByPatientId(patientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ClinicResponseDTO> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
        assertEquals("clinic123", responseBody.get(0).getId());
        assertEquals("clinic124", responseBody.get(1).getId());
    }

    @Test
    void deleteClinic_shouldReturnNoContent_whenClinicDeleted() {
        // Arrange
        String clinicId = "clinic123";
        doNothing().when(clinicService).deleteClinic(clinicId);

        // Act
        ResponseEntity<Void> response = clinicController.deleteClinic(clinicId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clinicService, times(1)).deleteClinic(clinicId);
    }
}
