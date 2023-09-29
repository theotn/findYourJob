package com.employerProfile.dto;

import com.employerProfile.enums.Role;
import lombok.Data;

@Data
public class UserDTO {

    private Integer id;

    private String email;

    private Boolean isActive;

    private Role role;
}
