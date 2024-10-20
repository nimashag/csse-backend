package com.medilink.api.models;

import com.medilink.api.enums.UserType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "receptionists")
@Data
@Getter
@Setter
@ToString
public class Receptionist extends User {
    private String allocatedHospitalId;

    public Receptionist() {
        super.setUserType(UserType.RECEPTIONIST);
    }

    public Receptionist(String id, String name, String email, String password, String allocatedHospitalId) {
        super(id, name, email, password, UserType.RECEPTIONIST);
        this.allocatedHospitalId = allocatedHospitalId;
    }
}
