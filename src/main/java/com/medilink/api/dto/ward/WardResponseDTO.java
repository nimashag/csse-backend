package com.medilink.api.dto.ward;

import com.medilink.api.models.Bed;
import com.medilink.api.models.Hospital;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardResponseDTO {
    private String wardId;
    private Hospital hospital;
    private int wardNo;
    private List<Bed> beds;
}
