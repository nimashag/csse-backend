package com.medilink.api.controllers;

import com.medilink.api.dto.labtest.LabTestRequestDTO;
import com.medilink.api.dto.labtest.LabTestResponseDTO;
import com.medilink.api.services.LabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labtests")
public class LabTestController {

    private final LabTestService labTestService;

    @Autowired
    public LabTestController(LabTestService labTestService) {
        this.labTestService = labTestService;
    }

    @PostMapping
    public ResponseEntity<LabTestResponseDTO> createLabTest(@RequestBody LabTestRequestDTO labTestRequestDTO) {
        return ResponseEntity.ok(labTestService.createLabTest(labTestRequestDTO));
    }

    @PutMapping("/{testId}")
    public ResponseEntity<LabTestResponseDTO> updateLabTest(@PathVariable String testId, @RequestBody LabTestRequestDTO labTestRequestDTO) {
        return ResponseEntity.ok(labTestService.updateLabTest(testId, labTestRequestDTO));
    }

    @GetMapping("/{testId}")
    public ResponseEntity<LabTestResponseDTO> getLabTestById(@PathVariable String testId) {
        return ResponseEntity.ok(labTestService.getLabTestById(testId));
    }

    @DeleteMapping("/{testId}")
    public ResponseEntity<Void> deleteLabTest(@PathVariable String testId) {
        boolean deleted = labTestService.deleteLabTest(testId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<LabTestResponseDTO>> getLabTestsByHospitalId(@PathVariable String hospitalId) {
        return ResponseEntity.ok(labTestService.getLabTestsByHospitalId(hospitalId));
    }

    @GetMapping
    public ResponseEntity<List<LabTestResponseDTO>> getAllLabTests() {
        return ResponseEntity.ok(labTestService.getAllLabTests());
    }
}
