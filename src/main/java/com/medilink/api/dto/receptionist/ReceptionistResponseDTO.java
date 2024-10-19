package com.medilink.api.dto.receptionist;

import com.medilink.api.models.Hospital;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceptionistResponseDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private Hospital hospital;
}
