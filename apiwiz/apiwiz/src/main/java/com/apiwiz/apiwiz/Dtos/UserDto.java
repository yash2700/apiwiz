package com.apiwiz.apiwiz.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String first_name;

    private String last_name;
    private String email;

    private String phone_number;

    private String password;
}
