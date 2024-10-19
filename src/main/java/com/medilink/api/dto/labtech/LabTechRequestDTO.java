package com.medilink.api.dto.labtech;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for creating/updating a Lab Technician.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabTechRequestDTO {
    private String name;
    private String email;
    private String password;
    private String allocatedHospitalId;
}
