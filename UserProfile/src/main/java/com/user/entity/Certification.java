package com.user.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String institution;

    private String name;

    private LocalDate startDate;

    private LocalDate expirationDate;

    private String city;

}
