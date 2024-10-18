package com.medilink.api.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Patient extends User {
    private String address;
    private int age;
    @Field("patientEmail")  // Custom field name to avoid collision
    private String email;
    private String gender;
    private String profileImage;
    private String contactNumber; // Newly added field
    private String emergencyDial; // Newly added field
    private List<Appointment> appointments;
    private List<MedicalHistory> medicalHistory;
}
