package com.user.service.impl;

import com.user.dto.*;
import com.user.entity.*;
import com.user.exception.BadRequestException;
import com.user.exception.NotFoundException;
import com.user.repository.UserProfileRepository;
import com.user.service.UserProfileService;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.OneToMany;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private UserProfileRepository userProfileRepository;
    private ModelMapper modelMapper;
    private RestTemplate restTemplate;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, ModelMapper modelMapper, RestTemplate restTemplate) {
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public UserProfileDTO createUserProfile(Integer userId) throws BadRequestException {

        Map<String, Integer> params = new HashMap<>();
        params.put("user", userId);

        User user = restTemplate.getForObject("http://localhost:8000/user?user={user}", User.class, params);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);

        userProfileRepository.save(userProfile);

        return modelMapper.map(userProfile, UserProfileDTO.class);
    }

    @Override
    public UserProfileDTO updateUserProfile(Integer userProfileId, UserProfileDTO userProfileDTO) throws NotFoundException {

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);

        UserProfile userProfile = userProfileOptional.orElseThrow(() -> new NotFoundException("Profile not found!"));

        if (userProfileDTO.getName() != null) userProfile.setName(userProfileDTO.getName());
        if (userProfileDTO.getPhoneNo() != null) userProfile.setPhoneNo(userProfileDTO.getPhoneNo());
        if (userProfileDTO.getDateOfBirth() != null) userProfile.setDateOfBirth(userProfileDTO.getDateOfBirth());
        if (userProfileDTO.getCity() != null) userProfile.setCity(userProfileDTO.getCity());
        if (userProfileDTO.getDescription() != null) userProfile.setDescription(userProfileDTO.getDescription());

        if (!userProfileDTO.getSkills().isEmpty()) {

            userProfile.setSkills(userProfileDTO.getSkills());
        }
        if (!userProfileDTO.getDomains().isEmpty()) {

            userProfile.setDomains(userProfileDTO.getDomains());
        }


        UserProfileDTO profile = modelMapper.map(userProfile, UserProfileDTO.class);

        return profile;
    }

    @Override
    public EducationDTO addEducationToProfile(Integer userProfileId, EducationDTO educationDTO) throws NotFoundException {

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);
        UserProfile userProfile = userProfileOptional.orElseThrow(() -> new NotFoundException("Profile not found!"));

        EducationDTO educationResponse = restTemplate.postForObject("http://localhost:8400/education", educationDTO, EducationDTO.class);
        userProfile.getEducation().add(modelMapper.map(educationResponse, Education.class));

        return educationResponse;
    }

    @Override
    public CertificationDTO addCertificationToProfile(Integer userProfileId, CertificationDTO certificationDTO) throws NotFoundException {

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);
        UserProfile userProfile = userProfileOptional.orElseThrow(() -> new NotFoundException("Profile not found!"));

        CertificationDTO certificationResponse = restTemplate.postForObject("http://localhost:8200/certification", certificationDTO, CertificationDTO.class);
        userProfile.getCertifications().add(modelMapper.map(certificationResponse, Certification.class));

        return certificationResponse;
    }

    @Override
    public ExperienceDTO addExperienceToProfile(Integer userProfileId, ExperienceDTO experienceDTO) throws NotFoundException {

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);
        UserProfile userProfile = userProfileOptional.orElseThrow(() -> new NotFoundException("Profile not found!"));

        ExperienceDTO experienceResponse = restTemplate.postForObject("http://localhost:8500/experience", experienceDTO, ExperienceDTO.class);
        userProfile.getExperiences().add(modelMapper.map(experienceResponse, Experience.class));

        return experienceResponse;
    }

    @Override
    public LanguageDTO addLanguageToProfile(Integer userProfileId, LanguageDTO languageDTO) throws NotFoundException {

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);
        UserProfile userProfile = userProfileOptional.orElseThrow(() -> new NotFoundException("Profile not found!"));

        LanguageDTO languageResponse = restTemplate.postForObject("http://localhost:8700/language", languageDTO, LanguageDTO.class);
        userProfile.getLanguages().add(modelMapper.map(languageResponse, Language.class));

        return languageResponse;
    }

    @Override
    public EducationDTO deleteEducationFromProfile(Integer userProfileId, Integer educationId) throws NotFoundException {

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);
        UserProfile userProfile = userProfileOptional.orElseThrow(() -> new NotFoundException("Profile not found!"));

        Map<String,Integer> params = new HashMap<>();
        params.put("education",educationId);

        EducationDTO educationDTO = restTemplate.getForObject("http://localhost:8400/education?education={education}",EducationDTO.class,params);
        Education educationToRemove = modelMapper.map(educationDTO,Education.class);

        userProfile.getEducation().remove(educationToRemove);

        return educationDTO;
    }

    @Override
    public CertificationDTO deleteCertificationFromProfile(Integer userProfileId, Integer certificationId) throws NotFoundException {

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);
        UserProfile userProfile = userProfileOptional.orElseThrow(() -> new NotFoundException("Profile not found!"));

        Map<String,Integer> params = new HashMap<>();
        params.put("certification",certificationId);

        CertificationDTO certificationDTO = restTemplate.getForObject("http://localhost:8200/certification?certification={certification}",CertificationDTO.class,params);
        Certification certificationToRemove = modelMapper.map(certificationDTO,Certification.class);

        userProfile.getCertifications().remove(certificationToRemove);

        return certificationDTO;

    }

    @Override
    public ExperienceDTO deleteExperienceFromProfile(Integer userProfileId, Integer experienceId) throws NotFoundException {

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);
        UserProfile userProfile = userProfileOptional.orElseThrow(() -> new NotFoundException("Profile not found!"));

        Map<String,Integer> params = new HashMap<>();
        params.put("experience",experienceId);

        ExperienceDTO experienceDTO = restTemplate.getForObject("http://localhost:8500/experience?experience={experience}",ExperienceDTO.class,params);
        Experience experienceToRemove = modelMapper.map(experienceDTO,Experience.class);

        userProfile.getExperiences().remove(experienceToRemove);

        return experienceDTO;
    }

    @Override
    public LanguageDTO deleteLanguageFromProfile(Integer userProfileId, Integer languageId) throws NotFoundException {

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);
        UserProfile userProfile = userProfileOptional.orElseThrow(() -> new NotFoundException("Profile not found!"));

        Map<String,Integer> params = new HashMap<>();
        params.put("language",languageId);

        LanguageDTO languageDTO = restTemplate.getForObject("http://localhost:8700/language?language={language}",LanguageDTO.class,params);
        Language languageToRemove = modelMapper.map(languageDTO,Language.class);

        userProfile.getLanguages().remove(languageToRemove);

        return languageDTO;
    }


}
