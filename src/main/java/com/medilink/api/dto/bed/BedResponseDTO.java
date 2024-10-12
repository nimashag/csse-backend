package com.medilink.api.dto.bed;

import com.medilink.api.enums.BedType;
import com.medilink.api.models.Patient;
import com.medilink.api.models.Ward;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BedResponseDTO {
    private String bedId;
    private Ward ward;
    private Patient patient;
    private BedType status;
}
