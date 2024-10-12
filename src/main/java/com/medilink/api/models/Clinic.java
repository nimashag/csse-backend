package com.medilink.api.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "clinics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Clinic {
    @Id
    private String id;
    private String clinicName;
    private Doctor doctor;
    private Hospital hospital;
    private List<Patient> patients = new ArrayList<>();

}
