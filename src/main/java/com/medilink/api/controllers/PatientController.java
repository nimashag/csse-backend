package com.medilink.api.controllers;

import com.medilink.api.dto.patient.PatientRequestDTO;
import com.medilink.api.dto.patient.PatientResponseDTO;
import com.medilink.api.dto.user.UserRequestDTO;
import com.medilink.api.dto.user.UserResponseDTO;
import com.medilink.api.enums.UserType;
import com.medilink.api.models.Hospital;
import com.medilink.api.models.Patient;
import com.medilink.api.services.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/patients")
public class PatientController {

    private final PatientService patientService;
    private final ModelMapper modelMapper;

    public PatientController(PatientService patientService, ModelMapper modelMapper) {
        this.patientService = patientService;
        this.modelMapper = modelMapper;
    }

    //create Patient
    @PostMapping("/create")
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody PatientRequestDTO patientRequestDTO) {
        Patient patient = modelMapper.map(patientRequestDTO, Patient.class);
        patient.setUserType(UserType.PATIENT);
        Patient savedPatient = patientService.savePatient(patient);
        PatientResponseDTO patientResponseDTO = modelMapper.map(savedPatient, PatientResponseDTO.class);
        return ResponseEntity.ok(patientResponseDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable("id") String id, @RequestBody PatientRequestDTO patientRequestDTO) {
        Patient patientToUpdate = modelMapper.map(patientRequestDTO, Patient.class);
        Patient updatedPatient = patientService.updatePatient(id, patientToUpdate);

        if (updatedPatient != null) {
            PatientResponseDTO responseDTO = modelMapper.map(updatedPatient, PatientResponseDTO.class);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") String id) {
        Patient existingPatient = patientService.getPatient(id);

        if (existingPatient != null) {
            patientService.deletePatient(id);
        }

        return null;
    }
}
