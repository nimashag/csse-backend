package com.medilink.api.models;

import com.medilink.api.enums.HospitalType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hospitals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Hospital {
    @Id
    private String hospitalId;
    private String hospitalName;
    private String hospitalEmail;
    private String area;
    private String contactNumber;
    private int noDoctors;
    private HospitalType hospitalType;
}
