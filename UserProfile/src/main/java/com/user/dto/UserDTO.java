package com.user.dto;

import com.user.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {

    private Integer id;
    private String email;
    private Boolean isActive;
    private Role role;
}
