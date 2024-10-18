package com.medilink.api.dto.doctors;

import lombok.Data;
import java.util.List;

@Data
public class DoctorRequestDTO {
    private String name;
    private String email;
    private String password; // Optional for updates
    private String specialization;
    private List<String> workingHospitals; // List of hospital IDs where the doctor works
}
