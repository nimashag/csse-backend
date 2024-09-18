package com.medilink.api.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String gender;
    private String profileImage;
    private List<Appointment> appointments;
    private List<MedicalHistory> medicalHistory;
}
