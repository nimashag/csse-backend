package com.medilink.api.dto.doctor;

import com.medilink.api.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) for creating/updating a Doctor.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequestDTO {
    private String name;
    private String email;
    private String password;
    private String specialization;  // Doctor-specific field
    private List<String> workingHospitals; // List of hospital IDs where the doctor works
}
