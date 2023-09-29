package com.education.service;

import com.education.dto.EducationDTO;
import com.education.entity.Education;
import com.education.exception.NotFoundException;

public interface EducationService {

    EducationDTO createEducation(EducationDTO educationDTO);
    EducationDTO getEducation(Integer educationId) throws NotFoundException;
    EducationDTO updateEducation(Integer educationId, EducationDTO educationDTO) throws NotFoundException;
    EducationDTO deleteEducation(Integer educationId) throws NotFoundException;
}
