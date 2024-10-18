package com.medilink.api.controllers;

import com.medilink.api.dto.doctors.DoctorRequestDTO;
import com.medilink.api.dto.doctors.DoctorResponseDTO;
import com.medilink.api.models.Doctor;
import com.medilink.api.services.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctors") // Ensure it starts with a slash
public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorController(DoctorService doctorService, ModelMapper modelMapper) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    // Create doctor
    @PostMapping("/")
    public ResponseEntity<DoctorResponseDTO> createDoctor(@RequestBody DoctorRequestDTO doctorRequest) {
        Doctor doctor = modelMapper.map(doctorRequest, Doctor.class);
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.ok(modelMapper.map(savedDoctor, DoctorResponseDTO.class));
    }

    // Update doctor
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@PathVariable String id, @RequestBody DoctorRequestDTO doctorRequest) {
        Doctor doctor = modelMapper.map(doctorRequest, Doctor.class);
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        return ResponseEntity.ok(modelMapper.map(updatedDoctor, DoctorResponseDTO.class));
    }

    // Delete doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable String id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    // Get doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctor(@PathVariable String id) {
        Doctor doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(modelMapper.map(doctor, DoctorResponseDTO.class));
    }

    // Get all doctors
    @GetMapping({"", "/"})// This could also be @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        List<DoctorResponseDTO> response = doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Get doctors by hospital ID
    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<DoctorResponseDTO>> getDoctorsByHospital(@PathVariable String hospitalId) {
        List<Doctor> doctors = doctorService.getDoctorsByHospitalId(hospitalId);
        List<DoctorResponseDTO> response = doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
