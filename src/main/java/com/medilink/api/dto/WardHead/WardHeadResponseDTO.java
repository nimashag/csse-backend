package com.medilink.api.dto.WardHead;

import com.medilink.api.models.Ward;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardHeadResponseDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private Ward ward;
}
