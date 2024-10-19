package com.medilink.api.controllers;

import com.medilink.api.dto.receptionist.ReceptionistRequestDTO;
import com.medilink.api.dto.receptionist.ReceptionistResponseDTO;
import com.medilink.api.models.Receptionist;
import com.medilink.api.models.User;
import com.medilink.api.services.ReceptionistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receptionist")
public class ReceptionistController {

    private final ReceptionistService receptionistService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReceptionistController(ReceptionistService receptionistService, ModelMapper modelMapper) {
        this.receptionistService = receptionistService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ReceptionistResponseDTO> createReceptionist(@RequestBody ReceptionistRequestDTO receptionistRequestDTO) {
        Receptionist receptionist = modelMapper.map(receptionistService, Receptionist.class);
        User savedReceptionist = receptionistService.saveReceptionist(receptionist);
        ReceptionistResponseDTO receptionistResponseDTO = modelMapper.map(savedReceptionist, ReceptionistResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(receptionistResponseDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<ReceptionistResponseDTO>> getAllReceptionist() {
        List<Receptionist> receptionists = receptionistService.getAllReceptionists();
        List<ReceptionistResponseDTO> receptionistResponseDTOS = receptionists.stream()
                .map(receptionist -> modelMapper.map(receptionist, ReceptionistResponseDTO.class))
                .toList();
        return ResponseEntity.ok(receptionistResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceptionistResponseDTO> getReceptionistById(@PathVariable("id") String id) {
        User receptionist = receptionistService.getReceptionist(id);
        if (receptionist == null) {
            return ResponseEntity.notFound().build();
        }
        ReceptionistResponseDTO receptionistResponseDTO = modelMapper.map(receptionist, ReceptionistResponseDTO.class);
        return ResponseEntity.ok(receptionistResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceptionistResponseDTO> updateReceptionist(@PathVariable("id") String id, @RequestBody ReceptionistRequestDTO receptionistRequestDTO) {
        Receptionist updateReceptionist = receptionistService.updateReceptionist(id, modelMapper.map(receptionistRequestDTO, Receptionist.class));
        if (updateReceptionist == null) {
            return ResponseEntity.notFound().build();
        }
        ReceptionistResponseDTO receptionistResponseDTO = modelMapper.map(updateReceptionist, ReceptionistResponseDTO.class);
        return ResponseEntity.ok(receptionistResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteReceptionist(@PathVariable("id") String id) {
        boolean deleted = receptionistService.deleteReceptionist(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
