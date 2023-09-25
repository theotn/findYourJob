package com.user.dto;

import com.user.entity.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserProfileDTO {

    private Integer id;

    private String name;

    private String phoneNo;

    private LocalDate dateOfBirth;

    private String city;

    private String description;

    private List<Experience> experiences;

    private List<String> domains;

    private List<Education> education;

    private List<Certification> certifications;

    private List<String> skills;

    private List<Language> languages;

    private List<UserProfileJob> jobs;

    private User user;
}
