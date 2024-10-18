package com.medilink.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.medilink.api.dto.patient.PatientResponseDTO;
import com.medilink.api.enums.BedType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "beds")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Bed {
    @Id
    private String bedId;
    private String wardId;
    private PatientResponseDTO patient;
    private BedType status = BedType.AVAILABLE;
}
