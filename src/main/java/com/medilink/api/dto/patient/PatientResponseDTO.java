package com.medilink.api.dto.patient;

import com.medilink.api.models.Appointment;
import com.medilink.api.models.MedicalHistory;
import lombok.Data;

import java.util.List;

@Data
public class PatientResponseDTO {
    private String id;
    private String name;
    private String email;
    private String profileImage;
    private String address;
    private int age;
    private String gender;
    private List<Appointment> appointments;
    private List<MedicalHistory> medicalHistory;
}
