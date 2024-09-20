package com.medilink.api.controllers;

import com.medilink.api.dto.doctor.DoctorRequestDTO;
import com.medilink.api.dto.doctor.DoctorResponseDTO;
import com.medilink.api.models.Doctor;
import com.medilink.api.services.DoctorService;
import com.medilink.api.services.EmailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class to manage doctor-related API endpoints.
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;  // Service for managing doctors
    private final ModelMapper modelMapper;  // Mapper for converting between DTOs and entities
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);  // Logger for logging requests and responses

    /**
     * Constructor for DoctorController.
     * @param doctorService Service for handling doctor operations.
     * @param modelMapper Mapper for converting between DTOs and entities.
     */
    @Autowired
    public DoctorController(DoctorService doctorService, ModelMapper modelMapper) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    @Autowired
    private EmailService emailService; // Service for sending emails

    /**
     * Creates a new doctor and sends a welcome email.
     * @param doctorRequestDTO Data Transfer Object for doctor creation.
     * @return ResponseEntity containing the created doctor.
     */
    @PostMapping({"", "/"})
    public ResponseEntity<DoctorResponseDTO> createDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        logger.info("[DOCTORS][POST] Incoming message. Creating new doctor: {}", doctorRequestDTO);

        Doctor doctor = modelMapper.map(doctorRequestDTO, Doctor.class);
        Doctor savedDoctor = doctorService.saveDoctor(doctor);

        // Sending welcome email
        try {
            emailService.sendDoctorWelcomeEmail(savedDoctor.getEmail(), savedDoctor.getName(), doctorRequestDTO.getPassword());
        } catch (Exception e) {
            logger.warn("[DOCTORS][EMAIL] Failed to send welcome email to {}: {}", savedDoctor.getEmail(), e.getMessage());
        }

        DoctorResponseDTO doctorResponseDTO = modelMapper.map(savedDoctor, DoctorResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorResponseDTO);
    }

    /**
     * Retrieves all doctors.
     * @return ResponseEntity containing a list of all doctors.
     */
    @GetMapping({"", "/"})
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
        logger.info("[DOCTORS][GET] Incoming message. Retrieving all doctors.");

        List<Doctor> doctors = doctorService.getAllDoctors();
        List<DoctorResponseDTO> doctorResponseDTOs = doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class))
                .toList();
        return ResponseEntity.ok(doctorResponseDTOs);
    }

    /**
     * Retrieves a doctor by ID.
     * @param id ID of the doctor to retrieve.
     * @return ResponseEntity containing the doctor details, or 404 if not found.
     */
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

    /**
     * Updates a doctor by ID.
     * @param id ID of the doctor to update.
     * @param doctorRequestDTO Data Transfer Object containing updated doctor information.
     * @return ResponseEntity containing the updated doctor details, or 404 if not found.
     */
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

    /**
     * Deletes a doctor by ID.
     * @param id ID of the doctor to delete.
     * @return ResponseEntity with no content, or 404 if not found.
     */
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

    /**
     * Assigns a hospital to a doctor.
     * @param doctorId ID of the doctor.
     * @param hospitalId ID of the hospital.
     * @return ResponseEntity with 200 OK on success, or 404 if not found.
     */
    @PostMapping("/{doctorId}/hospitals/{hospitalId}")
    public ResponseEntity<Void> assignHospitalToDoctor(@PathVariable String doctorId, @PathVariable String hospitalId) {
        logger.info("[DOCTORS][POST] Assigning hospital {} to doctor {}", hospitalId, doctorId);

        try {
            doctorService.addHospitalToDoctor(doctorId, hospitalId);
            return ResponseEntity.ok().build(); // Return 200 OK on success
        } catch (RuntimeException e) {
            logger.warn("[DOCTORS][POST] Failed to assign hospital to doctor: {}", e.getMessage());
            return ResponseEntity.notFound().build(); // Return 404 if doctor or hospital is not found
        }
    }

    /**
     * Unassigns a hospital from a doctor.
     * @param doctorId ID of the doctor.
     * @param hospitalId ID of the hospital.
     * @return ResponseEntity with 200 OK on success, or 404 if not found.
     */
    @DeleteMapping("/{doctorId}/hospitals/{hospitalId}")
    public ResponseEntity<Void> unassignHospitalFromDoctor(@PathVariable String doctorId, @PathVariable String hospitalId) {
        logger.info("[DOCTORS][DELETE] Unassigning hospital {} from doctor {}", hospitalId, doctorId);

        try {
            doctorService.removeHospitalFromDoctor(doctorId, hospitalId);
            return ResponseEntity.ok().build(); // Return 200 OK on success
        } catch (RuntimeException e) {
            logger.warn("[DOCTORS][DELETE] Failed to unassign hospital from doctor: {}", e.getMessage());
            return ResponseEntity.notFound().build(); // Return 404 if doctor or hospital is not found
        }
    }

    /**
     * Authenticates a doctor using email and password.
     * @param doctorRequestDTO Data Transfer Object containing doctor credentials.
     * @return ResponseEntity containing the authenticated doctor, or 401 Unauthorized if authentication fails.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<DoctorResponseDTO> authenticateDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        logger.info("[DOCTORS][AUTHENTICATE] Authenticating doctor with email: {}", doctorRequestDTO.getEmail());

        Doctor authenticatedDoctor = doctorService.authenticateDoctor(doctorRequestDTO.getEmail(), doctorRequestDTO.getPassword());
        if (authenticatedDoctor != null) {
            DoctorResponseDTO doctorResponseDTO = modelMapper.map(authenticatedDoctor, DoctorResponseDTO.class);
            return ResponseEntity.ok(doctorResponseDTO); // Return authenticated doctor
        } else {
            logger.warn("[DOCTORS][AUTHENTICATE] Authentication failed for email: {}", doctorRequestDTO.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Return 401 Unauthorized if authentication fails
        }
    }

    /**
     * Retrieves doctors by hospital ID.
     * @param hospitalId ID of the hospital to retrieve doctors from.
     * @return ResponseEntity containing a list of doctors associated with the specified hospital.
     */
    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<DoctorResponseDTO>> getDoctorsByHospital(@PathVariable String hospitalId) {
        List<Doctor> doctors = doctorService.getDoctorsByHospitalId(hospitalId);
        List<DoctorResponseDTO> response = doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}