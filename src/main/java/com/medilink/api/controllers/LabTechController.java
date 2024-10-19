package com.medilink.api.controllers;

import com.medilink.api.dto.labtech.LabTechRequestDTO;
import com.medilink.api.dto.labtech.LabTechResponseDTO;
import com.medilink.api.models.LabTech;
import com.medilink.api.services.LabTechService;
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
 * Controller class to manage lab technician-related API endpoints.
 */
@RestController
@RequestMapping("/api/labtechs")
public class LabTechController {

    private final LabTechService labTechService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(LabTechController.class);

    /**
     * Constructor for LabTechController.
     * @param labTechService Service for handling lab technician operations.
     * @param modelMapper Mapper for converting between DTOs and entities.
     */
    @Autowired
    public LabTechController(LabTechService labTechService, ModelMapper modelMapper) {
        this.labTechService = labTechService;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates a new lab technician and sends a welcome email.
     * @param labTechRequestDTO Data Transfer Object for lab technician creation.
     * @return ResponseEntity containing the created lab technician.
     */
    @PostMapping({"", "/"})
    public ResponseEntity<LabTechResponseDTO> createLabTech(@RequestBody LabTechRequestDTO labTechRequestDTO) {
        logger.info("[LABTECHS][POST] Incoming message. Creating new lab technician: {}", labTechRequestDTO);

        LabTech labTech = modelMapper.map(labTechRequestDTO, LabTech.class);
        LabTech savedLabTech = labTechService.saveLabTech(labTech);
        LabTechResponseDTO labTechResponseDTO = modelMapper.map(savedLabTech, LabTechResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(labTechResponseDTO);
    }

    /**
     * Retrieves all lab technicians.
     * @return ResponseEntity containing a list of all lab technicians.
     */
    @GetMapping({"", "/"})
    public ResponseEntity<List<LabTechResponseDTO>> getAllLabTechs() {
        logger.info("[LABTECHS][GET] Incoming message. Retrieving all lab technicians.");

        List<LabTech> labTechs = labTechService.getAllLabTechs();
        List<LabTechResponseDTO> labTechResponseDTOs = labTechs.stream()
                .map(labTech -> modelMapper.map(labTech, LabTechResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(labTechResponseDTOs);
    }

    /**
     * Retrieves a lab technician by ID.
     * @param id ID of the lab technician to retrieve.
     * @return ResponseEntity containing the lab technician details, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LabTechResponseDTO> getLabTechById(@PathVariable String id) {
        logger.info("[LABTECHS][GET] Incoming message. ID: {}", id);

        LabTech labTech = labTechService.getLabTechById(id);
        if (labTech == null) {
            logger.warn("[LABTECHS][GET] Lab technician with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
        LabTechResponseDTO labTechResponseDTO = modelMapper.map(labTech, LabTechResponseDTO.class);
        return ResponseEntity.ok(labTechResponseDTO);
    }

    /**
     * Updates a lab technician by ID.
     * @param id ID of the lab technician to update.
     * @param labTechRequestDTO Data Transfer Object containing updated lab technician information.
     * @return ResponseEntity containing the updated lab technician details, or 404 if not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LabTechResponseDTO> updateLabTech(@PathVariable String id, @RequestBody LabTechRequestDTO labTechRequestDTO) {
        logger.info("[LABTECHS][PUT] Incoming message. ID: {} body: {}", id, labTechRequestDTO);

        LabTech updatedLabTech = labTechService.updateLabTech(id, modelMapper.map(labTechRequestDTO, LabTech.class));
        if (updatedLabTech == null) {
            logger.warn("[LABTECHS][PUT] Unable to update. Lab technician with ID {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        LabTechResponseDTO labTechResponseDTO = modelMapper.map(updatedLabTech, LabTechResponseDTO.class);
        return ResponseEntity.ok(labTechResponseDTO);
    }

    /**
     * Deletes a lab technician by ID.
     * @param id ID of the lab technician to delete.
     * @return ResponseEntity with no content, or 404 if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabTech(@PathVariable String id) {
        logger.info("[LABTECHS][DELETE] Incoming message. ID: {}", id);
        boolean deleted = labTechService.deleteLabTech(id);
        if (!deleted) {
            logger.warn("[LABTECHS][DELETE] Unable to delete. Lab technician with ID {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Authenticates a lab technician using email and password.
     * @param labTechRequestDTO Data Transfer Object containing lab technician credentials.
     * @return ResponseEntity containing the authenticated lab technician, or 401 Unauthorized if authentication fails.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<LabTechResponseDTO> authenticateLabTech(@RequestBody LabTechRequestDTO labTechRequestDTO) {
        logger.info("[LABTECHS][AUTHENTICATE] Authenticating lab technician with email: {}", labTechRequestDTO.getEmail());

        LabTech authenticatedLabTech = labTechService.authenticateLabTech(labTechRequestDTO.getEmail(), labTechRequestDTO.getPassword());
        if (authenticatedLabTech != null) {
            LabTechResponseDTO labTechResponseDTO = modelMapper.map(authenticatedLabTech, LabTechResponseDTO.class);
            return ResponseEntity.ok(labTechResponseDTO); // Return authenticated lab technician
        } else {
            logger.warn("[LABTECHS][AUTHENTICATE] Authentication failed for email: {}", labTechRequestDTO.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Return 401 Unauthorized if authentication fails
        }
    }
}
