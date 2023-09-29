package com.feedback.entity;

import com.feedback.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private Boolean isActive;
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
