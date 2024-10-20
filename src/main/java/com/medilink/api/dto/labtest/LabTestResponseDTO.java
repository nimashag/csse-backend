package com.medilink.api.dto.labtest;

import lombok.*;

/**
 * Data Transfer Object (DTO) for returning LabTest information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabTestResponseDTO {
    private String testId;
    private String testName;
    private String status;
    private String result;
    private String hospitalId;
    private String patientId;
}
