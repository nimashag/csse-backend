package com.medilink.api.controllers;

import com.medilink.api.dto.clinic.ClinicRequestDTO;
import com.medilink.api.dto.clinic.ClinicResponseDTO;
import com.medilink.api.models.Clinic;
import com.medilink.api.services.ClinicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    private final ClinicService clinicService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClinicController(ClinicService clinicService, ModelMapper modelMapper) {
        this.clinicService = clinicService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ClinicResponseDTO> createClinic(@RequestBody ClinicRequestDTO clinicRequestDTO) {
        Clinic savedClinic = clinicService.saveClinic(clinicRequestDTO);
        ClinicResponseDTO clinicResponseDTO = modelMapper.map(savedClinic, ClinicResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(clinicResponseDTO);
    }

    @PostMapping("/{clinicId}/add-patient/{patientId}")
    public ResponseEntity<ClinicResponseDTO> addPatientToClinic(@PathVariable String clinicId, @PathVariable String patientId) {

        Clinic updatedClinic = clinicService.addPatientToClinic(clinicId, patientId);

        ClinicResponseDTO clinicResponseDTO = new ClinicResponseDTO();
        clinicResponseDTO.setId(updatedClinic.getId());
        clinicResponseDTO.setClinicName(updatedClinic.getClinicName());
        clinicResponseDTO.setDoctor(updatedClinic.getDoctor());
        clinicResponseDTO.setHospital(updatedClinic.getHospital());
        clinicResponseDTO.setPatients(updatedClinic.getPatients());

        return ResponseEntity.ok(clinicResponseDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<ClinicResponseDTO>> getAllClinics() {
        List<Clinic> clinics = clinicService.getClinics();

        List<ClinicResponseDTO> clinicResponseDTOs = clinics.stream()
                .map(clinic -> modelMapper.map(clinic, ClinicResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(clinicResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicResponseDTO> getClinics(@PathVariable("id") String id) {
        Clinic clinic = clinicService.getOneClinic(id);

        if (clinic == null) {
            return ResponseEntity.notFound().build();
        }

        ClinicResponseDTO clinicResponseDTO = modelMapper.map(clinic, ClinicResponseDTO.class);
        return ResponseEntity.ok(clinicResponseDTO);
    }

    @GetMapping("/patient/{patientId}/")
    public ResponseEntity<List<ClinicResponseDTO>> getClinicsByPatientId(@PathVariable String patientId) {
        List<Clinic> clinics = clinicService.getClinicsByPatientId(patientId);

        List<ClinicResponseDTO> clinicResponseDTOs = clinics.stream()
                .map(clinic -> modelMapper.map(clinic, ClinicResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(clinicResponseDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinic(@PathVariable String id) {
        clinicService.deleteClinic(id);
        return ResponseEntity.noContent().build();
    }



}
