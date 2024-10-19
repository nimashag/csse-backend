package com.medilink.api.dto.doctor;

import com.medilink.api.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for returning Doctor information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDTO {
    private String id;
    private String name;
    private String email;
    private UserType userType = UserType.DOCTOR;  // Automatically set to DOCTOR
    private String specialization;  // Doctor-specific field
    private List<String> workingHospitals; // List of hospital IDs where the doctor works
}
