package com.medilink.api.dto.labtech;

import com.medilink.api.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for returning Lab Technician information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabTechResponseDTO {
    private String id;
    private String name;
    private String email;
    private UserType userType = UserType.LAB_TECH; // Automatically set to LAB_TECH
    private String allocatedHospitalId;
}
