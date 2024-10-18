package com.medilink.api.controllers;

import com.medilink.api.dto.bed.BedRequestDTO;
import com.medilink.api.dto.bed.BedResponseDTO;
import com.medilink.api.models.Bed;
import com.medilink.api.services.BedService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/beds")
public class BedController {

    private final BedService bedService;
    private final ModelMapper modelMapper;


    public BedController(BedService bedService, ModelMapper modelMapper) {
        this.bedService = bedService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{wardId}")
    public ResponseEntity<List<Bed>> getBedsByWardId(@PathVariable String wardId) {
        List<Bed> beds = bedService.findBedsByWardId(wardId);
        return ResponseEntity.ok(beds);
    }

    @PutMapping("/update")
    public ResponseEntity<BedResponseDTO> updateBed(@RequestBody BedRequestDTO bedRequestDTO) {
        Bed updatedBed = bedService.updateBed(bedRequestDTO);
        BedResponseDTO bedResponseDTO = modelMapper.map(updatedBed, BedResponseDTO.class);

        return ResponseEntity.ok(bedResponseDTO);
    }

    @PutMapping("/empty/{id}")
    public ResponseEntity<BedResponseDTO> updateBed(@PathVariable String id) {
        Bed updatedBed = bedService.emptyBed(id);
        BedResponseDTO bedResponseDTO = modelMapper.map(updatedBed, BedResponseDTO.class);

        return ResponseEntity.ok(bedResponseDTO);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBed(@PathVariable String id) {
        bedService.deleteBed(id);
        return null;
    }
}
