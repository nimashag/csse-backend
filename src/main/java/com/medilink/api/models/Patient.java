package com.medilink.api.models;

import com.medilink.api.enums.UserType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "patients")
@Data
@Getter
@Setter
@ToString
public class Patient extends User{

    private String reason;
    private String reason_description;

    public Patient() {
        super.setUserType(UserType.PATIENT);
    }

    public Patient(String id, String name, String email, String password, String reason,String reason_description) {
        super(id, name, email, password, UserType.PATIENT);
        this.reason = reason;
        this.reason_description = reason_description;
    }
}
