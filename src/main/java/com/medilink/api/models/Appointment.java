package com.medilink.api.models;

import com.medilink.api.enums.PaymentStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Appointment {
    @Id
    private String appointmentId;
    private String patientId; // ID of the patient
    private String doctorId; // ID of the doctor
    private String hospitalId; // ID of the hospital
    private String date; // Appointment date
    private String time; // Appointment time
    private String type; // "public" or "private"
    private PaymentStatus paymentStatus; // Payment status
    private boolean isPaid; // If the appointment is paid
}
