package com.medilink.api.controllers;

import com.medilink.api.dto.hospital.HospitalRequestDTO;
import com.medilink.api.dto.hospital.HospitalResponseDTO;
import com.medilink.api.models.Hospital;
import com.medilink.api.services.EmailService;
import com.medilink.api.services.HospitalService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to manage hospital-related API endpoints.
 */
@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    private final HospitalService hospitalService; // Service to handle hospital-related operations
    private final ModelMapper modelMapper; // Mapper for converting between DTOs and entities
    private static final Logger logger = LoggerFactory.getLogger(HospitalController.class); // Logger for the class

    @Autowired
    public HospitalController(HospitalService hospitalService, ModelMapper modelMapper) {
        this.hospitalService = hospitalService;
        this.modelMapper = modelMapper;
    }

    @Autowired
    private EmailService emailService; // Service for sending emails

    /**
     * Creates a new hospital.
     * @param hospitalRequestDTO the data transfer object containing hospital details
     * @return a ResponseEntity containing the created HospitalResponseDTO and HTTP status
     */
    @PostMapping({"", "/"})
    public ResponseEntity<HospitalResponseDTO> createHospital(@RequestBody HospitalRequestDTO hospitalRequestDTO) {
        logger.info("[HOSPITALS][POST] Incoming message. Creating new hospital: {}", hospitalRequestDTO);

        Hospital hospital = modelMapper.map(hospitalRequestDTO, Hospital.class);
        Hospital savedHospital = hospitalService.saveHospital(hospital);

        // Sending welcome email
        try {
            emailService.sendHospitalWelcomeEmail(savedHospital.getHospitalEmail(), savedHospital.getHospitalName()); // Assuming savedHospital has email and name
        } catch (Exception e) {
            logger.warn("[HOSPITALS][EMAIL] Failed to send welcome email to {}: {}", savedHospital.getHospitalEmail(), e.getMessage());
        }

        HospitalResponseDTO hospitalResponseDTO = modelMapper.map(savedHospital, HospitalResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalResponseDTO);
    }

    /**
     * Retrieves all hospitals.
     * @return a ResponseEntity containing a list of HospitalResponseDTOs and HTTP status
     */
    @GetMapping({"", "/"})
    public ResponseEntity<List<HospitalResponseDTO>> getAllHospitals() {
        logger.info("[HOSPITALS][GET] Incoming message. Retrieving all hospitals.");

        List<Hospital> hospitals = hospitalService.getAllHospitals();
        List<HospitalResponseDTO> hospitalResponseDTOs = hospitals.stream()
                .map(hospital -> modelMapper.map(hospital, HospitalResponseDTO.class))
                .toList();
        return ResponseEntity.ok(hospitalResponseDTOs);
    }

    /**
     * Retrieves a specific hospital by its ID.
     * @param id the ID of the hospital to retrieve
     * @return a ResponseEntity containing the HospitalResponseDTO or not found status
     */
    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> getHospitalById(@PathVariable String id) {
        logger.info("[HOSPITALS][GET] Incoming message. ID: {}", id);

        Hospital hospital = hospitalService.getHospitalById(id);
        if (hospital == null) {
            logger.warn("[HOSPITALS][GET] Hospital with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
        HospitalResponseDTO hospitalResponseDTO = modelMapper.map(hospital, HospitalResponseDTO.class);
        return ResponseEntity.ok(hospitalResponseDTO);
    }

    /**
     * Updates an existing hospital by its ID.
     * @param id the ID of the hospital to update
     * @param hospitalRequestDTO the data transfer object containing updated hospital details
     * @return a ResponseEntity containing the updated HospitalResponseDTO or not found status
     */
    @PutMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> updateHospital(@PathVariable String id, @RequestBody HospitalRequestDTO hospitalRequestDTO) {
        logger.info("[HOSPITALS][PUT] Incoming message. ID: {} body: {} ", id, hospitalRequestDTO);

        Hospital updatedHospital = hospitalService.updateHospital(id, modelMapper.map(hospitalRequestDTO, Hospital.class));
        if (updatedHospital == null) {
            logger.warn("[HOSPITALS][PUT] Unable to update. Hospital with ID {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        HospitalResponseDTO hospitalResponseDTO = modelMapper.map(updatedHospital, HospitalResponseDTO.class);
        return ResponseEntity.ok(hospitalResponseDTO);
    }

    /**
     * Deletes a hospital by its ID.
     * @param id the ID of the hospital to delete
     * @return a ResponseEntity with no content or not found status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable String id) {
        logger.info("[HOSPITALS][DELETE] Incoming message. ID: {}", id);

        boolean deleted = hospitalService.deleteHospital(id);
        if (!deleted) {
            logger.warn("[HOSPITALS][DELETE] Unable to delete. Hospital with ID {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
