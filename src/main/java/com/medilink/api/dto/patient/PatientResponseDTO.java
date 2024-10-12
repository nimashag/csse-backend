package com.medilink.api.dto.patient;

import com.medilink.api.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {
    private String id;
    private String name;
    private String email;
    private UserType userType;
    private String reason;
    private String reason_description;
}
