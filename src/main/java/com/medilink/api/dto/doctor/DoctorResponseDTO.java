package com.medilink.api.dto.doctor;

import com.medilink.api.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDTO {
    private String id;
    private String name;
    private String email;
    private UserType userType = UserType.DOCTOR;  // Automatically set to DOCTOR
    private String specialization;  // Doctor-specific field
}
