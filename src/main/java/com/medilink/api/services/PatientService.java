package com.medilink.api.services;

import com.medilink.api.dto.patient.PatientRequestDTO;
import com.medilink.api.dto.patient.PatientResponseDTO;
import com.medilink.api.models.Patient;

import java.util.List;

public interface PatientService {
    PatientResponseDTO getPatientByEmail(String email);
    PatientResponseDTO createPatient(PatientRequestDTO patientDTO);
    PatientResponseDTO updatePatient(String patientId, PatientRequestDTO patientDTO);
    void deletePatient(String patientId);
    PatientResponseDTO getPatientById(String patientId);
    List<PatientResponseDTO> getAllPatients();
}
