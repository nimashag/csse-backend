package com.medilink.api.services;

import com.medilink.api.dto.labtest.LabTestRequestDTO;
import com.medilink.api.dto.labtest.LabTestResponseDTO;

import java.util.List;

/**
 * Service interface for managing LabTest entities.
 */
public interface LabTestService {
    LabTestResponseDTO createLabTest(LabTestRequestDTO labTestRequestDTO);
    LabTestResponseDTO updateLabTest(String testId, LabTestRequestDTO labTestRequestDTO);
    LabTestResponseDTO getLabTestById(String testId);
    boolean deleteLabTest(String testId);
    List<LabTestResponseDTO> getLabTestsByHospitalId(String hospitalId);
    List<LabTestResponseDTO> getAllLabTests();
}
