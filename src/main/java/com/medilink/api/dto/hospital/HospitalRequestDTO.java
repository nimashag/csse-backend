package com.medilink.api.dto.hospital;

import com.medilink.api.enums.HospitalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for creating/updating a Hospital.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalRequestDTO {
    private String hospitalName;
    private String hospitalEmail;
    private String area;
    private String contactNumber;
    private HospitalType hospitalType;
}
