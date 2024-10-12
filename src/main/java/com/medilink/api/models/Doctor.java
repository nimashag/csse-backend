package com.medilink.api.models;

import com.medilink.api.enums.UserType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "doctors")
@Data
@Getter
@Setter
@ToString
public class Doctor extends User {
    private String specialization;

    public Doctor() {
        super.setUserType(UserType.DOCTOR);
    }

    public Doctor(String id, String name, String email, String password, String specialization) {
        super(id, name, email, password, UserType.DOCTOR);
        this.specialization = specialization;
    }
}
