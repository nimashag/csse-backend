package com.medilink.api.models;

import com.medilink.api.enums.UserType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a Doctor entity.
 */
@EqualsAndHashCode(callSuper = true)
@Document(collection = "doctors")
@Data
@Getter
@Setter
@ToString
public class Doctor extends User {
    private String specialization;
    private List<String> workingHospitals = new ArrayList<>(); // List of hospital IDs where the doctor works

    public Doctor() {
        super.setUserType(UserType.DOCTOR);
    }

    public Doctor(String id, String name, String email, String password, String specialization, List<String> workingHospitals) {
        super(id, name, email, password, UserType.DOCTOR);
        this.specialization = specialization;
        this.workingHospitals = workingHospitals != null ? workingHospitals : new ArrayList<>(); // Ensure list is not null
    }
}
