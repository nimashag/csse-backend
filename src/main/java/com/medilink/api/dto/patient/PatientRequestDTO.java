package com.medilink.api.dto.patient;

import com.medilink.api.models.Appointment;
import com.medilink.api.models.MedicalHistory;
import lombok.Data;

import java.util.List;

@Data
public class PatientRequestDTO {
    private String name;
    private String email;
    private String password; // Newly added field
    private String contactNumber; // Newly added field
    private String emergencyDial; // Newly added field
    private String address;
    private int age;
    private String gender;
    private String profileImage;
    private List<Appointment> appointments;
    private List<MedicalHistory> medicalHistory;
}
