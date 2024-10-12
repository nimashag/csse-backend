package com.medilink.api.controllers;

import com.medilink.api.dto.ward.WardRequestDTO;
import com.medilink.api.dto.ward.WardResponseDTO;
import com.medilink.api.models.Ward;
import com.medilink.api.services.WardService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wards")
public class WardController {

    private final WardService wardService;
    private final ModelMapper modelMapper;

    public WardController(WardService wardService, ModelMapper modelMapper) {
        this.wardService = wardService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<WardResponseDTO> createWard(@RequestBody WardRequestDTO wardRequestDTO) {

        WardResponseDTO wardResponseDTO = wardService.createWard(wardRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(wardResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WardResponseDTO> getWardById(@PathVariable("id") String id) {
        Ward ward = wardService.getWard(id);
        if (ward == null) {
            return ResponseEntity.notFound().build();
        }
        WardResponseDTO wardResponseDTO = modelMapper.map(ward, WardResponseDTO.class);
        return ResponseEntity.ok(wardResponseDTO);

    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<WardResponseDTO>> getWardsByHospitalId(@PathVariable String hospitalId) {
        List<Ward> wards = wardService.getHospitalWard(hospitalId);
        List<WardResponseDTO> wardResponseDTOs = wards.stream()
                .map(ward -> modelMapper.map(ward, WardResponseDTO.class)) // Mapping User to UserResponseDTO
                .toList();
        return ResponseEntity.ok(wardResponseDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWard(@PathVariable("id") String id) {
        boolean deleted = wardService.deleteWard(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }



}
