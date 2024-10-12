package com.medilink.api.dto.bed;

import com.medilink.api.enums.BedType;
import com.medilink.api.models.Ward;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BedRequestDTO {
    private String bedId;
    private Ward ward;
    private String patientId;
    private BedType status;
}
