package com.medilink.api.dto.patient;

import com.medilink.api.models.Appointment;
import com.medilink.api.models.MedicalHistory;
import lombok.Data;

import java.util.List;

@Data
public class PatientRequestDTO {
    private String address;
    private int age;
    private String gender;
    private String profileImage;
    private List<Appointment> appointments;
    private List<MedicalHistory> medicalHistory;
}
