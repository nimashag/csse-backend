package com.medilink.api.models;

import com.medilink.api.enums.UserType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "wardHeads")
@Data
@Getter
@Setter
@ToString
public class WardHead extends User{
    private Ward ward;
    public WardHead() {
        super.setUserType(UserType.WARD_HEAD);
    }

    public WardHead(String id, String name, String email, String password, Ward ward) {
        super(id, name, email, password, UserType.WARD_HEAD);
        this.ward = ward;
    }
}
