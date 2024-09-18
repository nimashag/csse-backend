package com.medilink.api.controllers;

import com.medilink.api.dto.hospital.HospitalRequestDTO;
import com.medilink.api.dto.hospital.HospitalResponseDTO;
import com.medilink.api.models.Hospital;
import com.medilink.api.services.HospitalService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    public HospitalController(HospitalService hospitalService, ModelMapper modelMapper) {
        this.hospitalService = hospitalService;
        this.modelMapper = modelMapper;
    }

    // Create hospital
    @PostMapping({"", "/"})
    public ResponseEntity<HospitalResponseDTO> createHospital(@RequestBody HospitalRequestDTO hospitalRequestDTO) {
        Hospital hospital = modelMapper.map(hospitalRequestDTO, Hospital.class);
        Hospital savedHospital = hospitalService.saveHospital(hospital);
        HospitalResponseDTO hospitalResponseDTO = modelMapper.map(savedHospital, HospitalResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalResponseDTO);
    }

    // Get all hospitals
    @GetMapping({"", "/"})
    public ResponseEntity<List<HospitalResponseDTO>> getAllHospitals() {
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        List<HospitalResponseDTO> hospitalResponseDTOs = hospitals.stream()
                .map(hospital -> modelMapper.map(hospital, HospitalResponseDTO.class)) // Mapping Hospital to HospitalResponseDTO
                .toList(); // Collect to List
        return ResponseEntity.ok(hospitalResponseDTOs);
    }

    // Get hospital by ID
    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> getHospitalById(@PathVariable String id) {
        Hospital hospital = hospitalService.getHospitalById(id);
        if (hospital == null) {
            return ResponseEntity.notFound().build();
        }
        HospitalResponseDTO hospitalResponseDTO = modelMapper.map(hospital, HospitalResponseDTO.class);
        return ResponseEntity.ok(hospitalResponseDTO);
    }

    // Update hospital by ID
    @PutMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> updateHospital(@PathVariable String id, @RequestBody HospitalRequestDTO hospitalRequestDTO) {
        Hospital updatedHospital = hospitalService.updateHospital(id, modelMapper.map(hospitalRequestDTO, Hospital.class));
        if (updatedHospital == null) {
            return ResponseEntity.notFound().build();
        }
        HospitalResponseDTO hospitalResponseDTO = modelMapper.map(updatedHospital, HospitalResponseDTO.class);
        return ResponseEntity.ok(hospitalResponseDTO);
    }

    // Delete hospital by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable String id) {
        boolean deleted = hospitalService.deleteHospital(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
