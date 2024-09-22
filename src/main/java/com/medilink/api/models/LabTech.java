package com.medilink.api.models;

import com.medilink.api.enums.UserType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a LabTech entity.
 */
@EqualsAndHashCode(callSuper = true)
@Document(collection = "labtechs")
@Data
@Getter
@Setter
@ToString
public class LabTech extends User {
    private String allocatedHospitalId;

    public LabTech() {
        super.setUserType(UserType.LAB_TECH);
    }

    public LabTech(String id, String name, String email, String password, String allocatedHospitalId) {
        super(id, name, email, password, UserType.LAB_TECH);
        this.allocatedHospitalId = allocatedHospitalId;
    }
}