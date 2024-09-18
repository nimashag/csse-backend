package com.medilink.api.models;

import com.medilink.api.enums.UserType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    public UserType userType;

}
