package com.medilink.api.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "queues")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Queue {
    @Id
    private String id;
    private String patientId;
    private String appointmentId;
    private int position;
    private String status;
}
