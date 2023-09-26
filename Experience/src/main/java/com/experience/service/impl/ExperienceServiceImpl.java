package com.experience.service.impl;

import com.experience.dto.ExperienceDTO;
import com.experience.entity.Experience;
import com.experience.repository.ExperienceRepository;
import com.experience.service.ExperienceService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ExperienceServiceImpl implements ExperienceService {

    private ExperienceRepository experienceRepository;

    private ModelMapper modelMapper;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, ModelMapper modelMapper) {
        this.experienceRepository = experienceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ExperienceDTO createExperience(ExperienceDTO experienceDTO) {

        Experience experience = modelMapper.map(experienceDTO,Experience.class);
        experienceRepository.save(experience);

        return modelMapper.map(experience,ExperienceDTO.class);
    }

}
