package com.medilink.api.dto.appointment;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {
    private String appointmentId;
    private String patientId; // Include patientId if necessary
    private String doctorId;
    private String hospitalId;
    private String date;
    private String time;
    private String type; // public/private
    private String paymentStatus; // Pending, Completed, Failed
    private boolean isPaid; // If the appointment is paid
}
