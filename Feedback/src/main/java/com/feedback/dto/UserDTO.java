package com.feedback.dto;

import com.feedback.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {


    private Integer id;

    @Email(message = "{user.email.invalid}")
    private String email;

    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private Role role;

}
