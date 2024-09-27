package com.medilink.api.Impl;

import com.medilink.api.dto.labtest.LabTestRequestDTO;
import com.medilink.api.dto.labtest.LabTestResponseDTO;
import com.medilink.api.models.LabTest;
import com.medilink.api.repositories.LabTestRepository;
import com.medilink.api.services.LabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabTestServiceImpl implements LabTestService {

    private final LabTestRepository labTestRepository;

    @Autowired
    public LabTestServiceImpl(LabTestRepository labTestRepository) {
        this.labTestRepository = labTestRepository;
    }

    @Override
    public LabTestResponseDTO createLabTest(LabTestRequestDTO labTestRequestDTO) {
        LabTest labTest = new LabTest(null, labTestRequestDTO.getTestName(), labTestRequestDTO.getStatus(),
                labTestRequestDTO.getResult(), labTestRequestDTO.getHospitalId(), labTestRequestDTO.getPatientId());
        LabTest savedLabTest = labTestRepository.save(labTest);
        return mapToResponseDTO(savedLabTest);
    }

    @Override
    public LabTestResponseDTO updateLabTest(String testId, LabTestRequestDTO labTestRequestDTO) {
        LabTest labTest = labTestRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("LabTest not found"));
        labTest.setTestName(labTestRequestDTO.getTestName());
        labTest.setStatus(labTestRequestDTO.getStatus());
        labTest.setResult(labTestRequestDTO.getResult());
        labTest.setHospitalId(labTestRequestDTO.getHospitalId());
        LabTest updatedLabTest = labTestRepository.save(labTest);
        return mapToResponseDTO(updatedLabTest);
    }

    @Override
    public LabTestResponseDTO getLabTestById(String testId) {
        LabTest labTest = labTestRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("LabTest not found"));
        return mapToResponseDTO(labTest);
    }

    @Override
    public boolean deleteLabTest(String testId) {
        if (labTestRepository.existsById(testId)) {
            labTestRepository.deleteById(testId);
            return true;
        }
        return false;
    }

    @Override
    public List<LabTestResponseDTO> getLabTestsByHospitalId(String hospitalId) {
        return labTestRepository.findByHospitalId(hospitalId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LabTestResponseDTO> getAllLabTests() {
        return labTestRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private LabTestResponseDTO mapToResponseDTO(LabTest labTest) {
        return new LabTestResponseDTO(
                labTest.getTestId(),
                labTest.getTestName(),
                labTest.getStatus(),
                labTest.getResult(),
                labTest.getHospitalId(),
                labTest.getPatientId()
        );
    }
}
