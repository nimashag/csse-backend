package com.medilink.api.controllers;

import com.medilink.api.dto.doctor.DoctorRequestDTO;
import com.medilink.api.dto.doctor.DoctorResponseDTO;
import com.medilink.api.models.Doctor;
import com.medilink.api.services.DoctorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    public DoctorController(DoctorService doctorService, ModelMapper modelMapper) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    // Create doctor
    @PostMapping({"", "/"})
    public ResponseEntity<DoctorResponseDTO> createDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        logger.info("[DOCTORS][POST] Incoming message. Creating new doctor: {}", doctorRequestDTO);

        Doctor doctor = modelMapper.map(doctorRequestDTO, Doctor.class);
        Doctor savedDoctor = doctorService.saveDoctor(doctor);
        DoctorResponseDTO doctorResponseDTO = modelMapper.map(savedDoctor, DoctorResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorResponseDTO);
    }

    // Get all doctors
    @GetMapping({"", "/"})
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
        logger.info("[DOCTORS][GET] Incoming message. Retrieving all doctors.");

        List<Doctor> doctors = doctorService.getAllDoctors();
        List<DoctorResponseDTO> doctorResponseDTOs = doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class))
                .toList();
        return ResponseEntity.ok(doctorResponseDTOs);
    }

    // Get doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable String id) {
        logger.info("[DOCTORS][GET] Incoming message. ID: {}", id);

        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            logger.warn("[DOCTORS][GET] Doctor with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
        DoctorResponseDTO doctorResponseDTO = modelMapper.map(doctor, DoctorResponseDTO.class);
        return ResponseEntity.ok(doctorResponseDTO);
    }

    // Update doctor by ID
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@PathVariable String id, @RequestBody DoctorRequestDTO doctorRequestDTO) {
        logger.info("[DOCTORS][PUT] Incoming message. ID: {} body: {}", id, doctorRequestDTO);

        Doctor updatedDoctor = doctorService.updateDoctor(id, modelMapper.map(doctorRequestDTO, Doctor.class));
        if (updatedDoctor == null) {
            logger.warn("[DOCTORS][PUT] Unable to update. Doctor with ID {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        DoctorResponseDTO doctorResponseDTO = modelMapper.map(updatedDoctor, DoctorResponseDTO.class);
        return ResponseEntity.ok(doctorResponseDTO);
    }

    // Delete doctor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable String id) {
        logger.info("[DOCTORS][DELETE] Incoming message. ID: {}", id);

        boolean deleted = doctorService.deleteDoctor(id);
        if (!deleted) {
            logger.warn("[DOCTORS][DELETE] Unable to delete. Doctor with ID {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
