package com.medilink.api.dto.labtest;

import lombok.*;

/**
 * Data Transfer Object (DTO) for creating/updating a LabTest.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabTestRequestDTO {
    private String testName;
    private String status;
    private String result;
    private String hospitalId;
    private String patientId;
}
