package com.medilink.api.services;

import com.medilink.api.dto.clinic.ClinicRequestDTO;
import com.medilink.api.models.Clinic;
import com.medilink.api.models.Doctor;
import com.medilink.api.models.Hospital;
import com.medilink.api.models.Patient;
import com.medilink.api.repositories.ClinicRepository;
import com.medilink.api.repositories.DoctorRepository;
import com.medilink.api.repositories.HospitalRepository;
import com.medilink.api.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClinicServiceTest {

    @InjectMocks
    private ClinicService clinicService;

    @Mock
    private ClinicRepository clinicRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveClinic_shouldReturnSavedClinic() {
        // Arrange
        ClinicRequestDTO clinicRequestDTO = new ClinicRequestDTO();
        clinicRequestDTO.setClinicName("Health Clinic");
        clinicRequestDTO.setDoctorId("doctorId1");
        clinicRequestDTO.setHospitalId("hospitalId1");

        Doctor doctor = new Doctor(); doctor.setId("doctorId1");
        Hospital hospital = new Hospital(); hospital.setHospitalId("hospitalId1");

        Clinic clinic = new Clinic();
        clinic.setClinicName("Health Clinic");
        clinic.setDoctor(doctor);
        clinic.setHospital(hospital);

        when(doctorRepository.findById("doctorId1")).thenReturn(Optional.of(doctor));
        when(hospitalRepository.findById("hospitalId1")).thenReturn(Optional.of(hospital));
        when(clinicRepository.save(any(Clinic.class))).thenReturn(clinic);

        // Act
        Clinic savedClinic = clinicService.saveClinic(clinicRequestDTO);

        // Assert
        assertNotNull(savedClinic);
        assertEquals("Health Clinic", savedClinic.getClinicName());
        assertEquals(doctor, savedClinic.getDoctor());
        assertEquals(hospital, savedClinic.getHospital());
    }

    @Test
    void addPatientToClinic_shouldAddPatientAndReturnUpdatedClinic() {
        // Arrange
        String clinicId = "clinic1";
        String patientId = "patient1";

        Clinic clinic = new Clinic();
        clinic.setId(clinicId);
        List<Patient> patients = new ArrayList<>();
        clinic.setPatients(patients);

        Patient patient = new Patient(); patient.setId(patientId);

        when(clinicRepository.findById(clinicId)).thenReturn(Optional.of(clinic));
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(clinicRepository.save(clinic)).thenReturn(clinic);

        // Act
        Clinic updatedClinic = clinicService.addPatientToClinic(clinicId, patientId);

        // Assert
        assertNotNull(updatedClinic);
        assertEquals(1, updatedClinic.getPatients().size());
        assertEquals(patientId, updatedClinic.getPatients().get(0).getId());
    }

    @Test
    void getClinics_shouldReturnListOfAllClinics() {
        // Arrange
        Clinic clinic1 = new Clinic();
        Clinic clinic2 = new Clinic();
        List<Clinic> clinics = List.of(clinic1, clinic2);

        when(clinicRepository.findAll()).thenReturn(clinics);

        // Act
        List<Clinic> result = clinicService.getClinics();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getOneClinic_shouldReturnClinic_whenExists() {
        // Arrange
        String clinicId = "clinic1";
        Clinic clinic = new Clinic(); clinic.setId(clinicId);

        when(clinicRepository.findById(clinicId)).thenReturn(Optional.of(clinic));

        // Act
        Clinic result = clinicService.getOneClinic(clinicId);

        // Assert
        assertNotNull(result);
        assertEquals(clinicId, result.getId());
    }

    @Test
    void getOneClinic_shouldThrowException_whenClinicNotFound() {
        // Arrange
        String clinicId = "clinic1";

        when(clinicRepository.findById(clinicId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> clinicService.getOneClinic(clinicId));
        assertEquals("Clinic not found", exception.getMessage());
    }

    @Test
    void getClinicsByPatientId_shouldReturnClinicsContainingPatient() {
        // Arrange
        String patientId = "patient1";
        Patient patient = new Patient(); patient.setId(patientId);

        Clinic clinic1 = new Clinic();
        clinic1.setPatients(List.of(patient));

        Clinic clinic2 = new Clinic();
        List<Clinic> allClinics = List.of(clinic1, clinic2);

        when(clinicRepository.findAll()).thenReturn(allClinics);

        // Act
        List<Clinic> result = clinicService.getClinicsByPatientId(patientId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(patientId, result.get(0).getPatients().get(0).getId());
    }

    @Test
    void deleteClinic_shouldCallRepositoryDeleteById() {
        // Arrange
        String clinicId = "clinic1";

        doNothing().when(clinicRepository).deleteById(clinicId);

        // Act
        clinicService.deleteClinic(clinicId);

        // Assert
        verify(clinicRepository, times(1)).deleteById(clinicId);
    }
}
