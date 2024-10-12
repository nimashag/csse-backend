package com.medilink.api.dto.clinic;

import com.medilink.api.models.Doctor;
import com.medilink.api.models.Hospital;
import com.medilink.api.models.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicResponseDTO {
    private String id;
    private String clinicName;
    private Doctor doctor;
    private Hospital hospital;
    private List<Patient> patients = new ArrayList<>();

}
