package com.experience.api;

import com.experience.dto.ExperienceDTO;
import com.experience.service.ExperienceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/experience")
public class ExperienceController {

    private ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    public ResponseEntity<ExperienceDTO> createExperience(@RequestBody ExperienceDTO experienceDTO){

        ExperienceDTO experience = experienceService.createExperience(experienceDTO);
        return new ResponseEntity<>(experience, HttpStatus.CREATED);
    }
}

