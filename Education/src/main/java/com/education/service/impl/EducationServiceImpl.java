package com.education.service.impl;

import com.education.dto.EducationDTO;
import com.education.entity.Education;
import com.education.repository.EducationRepository;
import com.education.service.EducationService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EducationServiceImpl implements EducationService {

    private EducationRepository educationRepository;

    private ModelMapper modelMapper;

    public EducationServiceImpl(EducationRepository educationRepository, ModelMapper modelMapper) {
        this.educationRepository = educationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EducationDTO createEducation(EducationDTO educationDTO) {

        Education education = modelMapper.map(educationDTO,Education.class);
        educationRepository.save(education);

        return modelMapper.map(education,EducationDTO.class);
    }

}
