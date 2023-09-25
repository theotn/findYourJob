package com.education.api;

import com.education.dto.EducationDTO;
import com.education.entity.Education;
import com.education.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/education")
public class EducationController {

    private EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping
    public ResponseEntity<EducationDTO> createEducation(@RequestBody EducationDTO educationDTO){

        EducationDTO education = educationService.createEducation(educationDTO);
        return new ResponseEntity<>(education, HttpStatus.CREATED);
    }
}
