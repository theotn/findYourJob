package com.employerProfile.dto;

import lombok.Data;

@Data
public class FeedbackDTO {
    private Integer id;
    private String description;
    private Integer stars;
    private Integer reports;
}
