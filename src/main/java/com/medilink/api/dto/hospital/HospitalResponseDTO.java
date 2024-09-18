package com.medilink.api.dto.hospital;

import com.medilink.api.enums.HospitalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalResponseDTO {
    private String hospitalId;
    private String hospitalName;
    private String hospitalEmail;
    private String area;
    private String contactNumber;
    private int noDoctors;
    private HospitalType hospitalType; // Include hospitalType in the response
}
