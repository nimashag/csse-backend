package com.medilink.api.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a LabTest entity.
 */
@Document(collection = "labtests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LabTest {
    @Id
    private String testId;
    private String testName;
    private String status;
    private String result;
    private String hospitalId;
    private String patientId;
}
