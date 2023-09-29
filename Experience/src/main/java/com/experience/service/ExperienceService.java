package com.experience.service;

import com.experience.dto.ExperienceDTO;
import com.experience.entity.Experience;
import com.experience.exception.NotFoundException;
import jakarta.validation.metadata.ExecutableDescriptor;

public interface ExperienceService {

    ExperienceDTO createExperience(ExperienceDTO experienceDTO);

    ExperienceDTO getExperience(Integer experienceId) throws NotFoundException;

    ExperienceDTO updateExperience(Integer experienceId, ExperienceDTO experienceDTO) throws NotFoundException;

    ExperienceDTO deleteExperience(Integer experienceId) throws NotFoundException;
}
