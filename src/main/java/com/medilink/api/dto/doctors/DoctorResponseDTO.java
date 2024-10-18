package com.medilink.api.dto.doctors;

import lombok.Data;
import java.util.List;

@Data
public class DoctorResponseDTO {
    private String id;
    private String name;
    private String email;
    private String specialization;
    private List<String> workingHospitals; // List of hospital IDs where the doctor works
}

