package com.medilink.api.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    private String appointmentId;
    private String hospitalId;
    private Date appointmentDate;
    private String status;
    private boolean isPublicHospital;
    private boolean isConfirmed;
}
