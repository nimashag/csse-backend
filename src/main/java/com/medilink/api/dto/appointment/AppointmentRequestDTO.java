package com.medilink.api.dto.appointment;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {
    private String doctorId;
    private String hospitalId;
    private String date;
    private String time;
    private String type; // public/private
    private boolean isPaid;
}
