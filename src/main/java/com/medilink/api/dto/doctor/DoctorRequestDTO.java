package com.medilink.api.dto.doctor;

import com.medilink.api.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequestDTO {
    private String name;
    private String email;
    private String password;
    private String specialization;  // Doctor-specific field
}
