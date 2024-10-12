package com.medilink.api.dto.ward;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardRequestDTO {
    private String hospitalId;
    private int wardNo;
    private int beds;
}
