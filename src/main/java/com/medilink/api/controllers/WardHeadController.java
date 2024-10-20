package com.medilink.api.controllers;

import com.medilink.api.dto.WardHead.WardHeadRequestDTO;
import com.medilink.api.dto.WardHead.WardHeadResponseDTO;
import com.medilink.api.models.WardHead;
import com.medilink.api.services.WardHeadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/wardHead")
public class WardHeadController {

    private final WardHeadService wardHeadService;
    private final ModelMapper modelMapper;

    @Autowired
    public WardHeadController(WardHeadService wardHeadService, ModelMapper modelMapper) {
        this.wardHeadService = wardHeadService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<WardHeadResponseDTO> createWardHead(@RequestBody WardHeadRequestDTO wardHeadRequestDTO) {
        WardHead wardHead = modelMapper.map(wardHeadRequestDTO, WardHead.class);
        WardHead savedWardHead = wardHeadService.saveWardHead(wardHead);
        WardHeadResponseDTO wardHeadResponseDTO = modelMapper.map(savedWardHead, WardHeadResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(wardHeadResponseDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<WardHeadResponseDTO>> getAllWardHead() {
        List<WardHead> wardHeads = wardHeadService.getAllWardHead();
        List<WardHeadResponseDTO> wardHeadResponseDTOS = wardHeads.stream()
                .map(wardHead -> modelMapper.map(wardHead, WardHeadResponseDTO.class))
                .toList();
        return ResponseEntity.ok(wardHeadResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WardHeadResponseDTO> getWardHead(@PathVariable("id") String id) {
        WardHead wardHead = wardHeadService.getWardHead(id);
        if (wardHead == null) {
            return ResponseEntity.notFound().build();
        }
        WardHeadResponseDTO wardHeadResponseDTO = modelMapper.map(wardHead, WardHeadResponseDTO.class);
        return ResponseEntity.ok(wardHeadResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WardHeadResponseDTO> updateWardHead(@PathVariable("id") String id, @RequestBody WardHeadRequestDTO wardHeadRequestDTO) {
        WardHead updateWardHead = wardHeadService.updateWardHead(id, modelMapper.map(wardHeadRequestDTO, WardHead.class));
        if (updateWardHead == null) {
            return ResponseEntity.notFound().build();
        }
        WardHeadResponseDTO wardHeadResponseDTO = modelMapper.map(updateWardHead, WardHeadResponseDTO.class);
        return ResponseEntity.ok(wardHeadResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteWardHead(@PathVariable("id") String id) {
        boolean deleted = wardHeadService.deleteWardHead(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
