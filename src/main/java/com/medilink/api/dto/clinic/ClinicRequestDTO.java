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
public class ClinicRequestDTO {
    private String id;
    private String clinicName;
    private String doctorId;
    private String hospitalId;
    private List<Patient> patients = new ArrayList<>();
}
