package com.medilink.api.dto.WardHead;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardHeadRequestDTO {
    private String name;
    private String email;
    private String password;
    private String wardId;
}
