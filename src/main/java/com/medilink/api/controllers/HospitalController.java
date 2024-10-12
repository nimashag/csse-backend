package com.medilink.api.controllers;

import com.medilink.api.dto.hospital.HospitalRequestDTO;
import com.medilink.api.dto.hospital.HospitalResponseDTO;
import com.medilink.api.models.Hospital;
import com.medilink.api.services.HospitalService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(HospitalController.class);

    @Autowired
    public HospitalController(HospitalService hospitalService, ModelMapper modelMapper) {
        this.hospitalService = hospitalService;
        this.modelMapper = modelMapper;
    }

    // Create hospital
    @PostMapping({"", "/"})
    public ResponseEntity<HospitalResponseDTO> createHospital(@RequestBody HospitalRequestDTO hospitalRequestDTO) {
        logger.info("[HOSPITALS][POST] Incoming message. Creating new hospital: {}", hospitalRequestDTO);

        Hospital hospital = modelMapper.map(hospitalRequestDTO, Hospital.class);
        Hospital savedHospital = hospitalService.saveHospital(hospital);
        HospitalResponseDTO hospitalResponseDTO = modelMapper.map(savedHospital, HospitalResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalResponseDTO);
    }

    // Get all hospitals
    @GetMapping({"", "/"})
    public ResponseEntity<List<HospitalResponseDTO>> getAllHospitals() {
        logger.info("[HOSPITALS][GET] Incoming message. Retrieving all hospitals.");

        List<Hospital> hospitals = hospitalService.getAllHospitals();
        List<HospitalResponseDTO> hospitalResponseDTOs = hospitals.stream()
                .map(hospital -> modelMapper.map(hospital, HospitalResponseDTO.class))
                .toList();
        return ResponseEntity.ok(hospitalResponseDTOs);
    }

    // Get hospital by ID
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

    // Update hospital by ID
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

    // Delete hospital by ID
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
