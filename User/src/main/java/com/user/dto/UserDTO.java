package com.user.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Integer id;

    private String email;

    private String password;

    private Boolean isActive;
}
