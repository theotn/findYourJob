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

        Map<String,Integer> params = new HashMap<>();
        params.put("user",userId);

        User user = restTemplate.getForObject("http://localhost:8000/user?user={user}",User.class,params);
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);

        userProfileRepository.save(userProfile);

        return modelMapper.map(userProfile,UserProfileDTO.class);
    }
    @Override
    public UserProfileDTO updateUserProfile(Integer userProfileId, UserProfileDTO userProfileDTO) throws NotFoundException {

        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(userProfileId);

        UserProfile userProfile = userProfileOptional.orElseThrow(()-> new NotFoundException("Profile not found!"));

        if(userProfileDTO.getName() != null) userProfile.setName(userProfileDTO.getName());
        if(userProfileDTO.getPhoneNo() != null) userProfile.setPhoneNo(userProfileDTO.getPhoneNo());
        if(userProfileDTO.getDateOfBirth() != null) userProfile.setDateOfBirth(userProfileDTO.getDateOfBirth());
        if(userProfileDTO.getCity() != null) userProfile.setCity(userProfileDTO.getCity());
        if(userProfileDTO.getDescription() != null) userProfile.setDescription(userProfileDTO.getDescription());

        if(!userProfileDTO.getSkills().isEmpty()){
            for(String skill : userProfileDTO.getSkills()) {
                userProfile.getSkills().add(skill);
            }
        }
        if(!userProfileDTO.getDomains().isEmpty()){
            for(String domain : userProfileDTO.getDomains()) {
                userProfile.getDomains().add(domain);
            }
        }

        List<EducationDTO> educationDTOList = new ArrayList<>();

        if(!userProfileDTO.getEducationDTO().isEmpty()) {
            educationDTOList = addEducation(userProfile, userProfileDTO.getEducationDTO());
        }

        List<CertificationDTO> certificationDTOList = new ArrayList<>();
        if(!userProfileDTO.getCertificationDTO().isEmpty()){
            certificationDTOList = addCertification(userProfile, userProfileDTO.getCertificationDTO());
        }

        List<ExperienceDTO> experienceDTOList = new ArrayList<>();
        if(userProfileDTO.getExperienceDTO()!=null && !userProfileDTO.getExperienceDTO().isEmpty()){
            experienceDTOList = addExperience(userProfile,userProfileDTO.getExperienceDTO());
        }

        UserProfileDTO profile = modelMapper.map(userProfile, UserProfileDTO.class);
        profile.setEducationDTO(educationDTOList);
        profile.setCertificationDTO(certificationDTOList);
        profile.setExperienceDTO(experienceDTOList);

        return profile;
    }


    public List<EducationDTO> addEducation(UserProfile userProfile, List<EducationDTO> educationDTO) throws NotFoundException {

        List<EducationDTO> educationDTOList = new ArrayList<>();

        for(EducationDTO e : educationDTO) {
            EducationDTO educationResponse = restTemplate.postForObject("http://localhost:8400/education", e, EducationDTO.class);
            Education education = modelMapper.map(educationResponse,Education.class);
            userProfile.getEducation().add(education);
            educationDTOList.add(educationResponse);
        }

        return educationDTOList;
    }

    public List<CertificationDTO> addCertification(UserProfile userProfile, List<CertificationDTO> certificationDTO) throws NotFoundException {

        List<CertificationDTO> certificationDTOList = new ArrayList<>();
        for(CertificationDTO c : certificationDTO) {
            CertificationDTO certificationResponse = restTemplate.postForObject("http://localhost:8200/certification", c, CertificationDTO.class);
            Certification certification = modelMapper.map(certificationResponse, Certification.class);
            userProfile.getCertifications().add(certification);
            certificationDTOList.add(certificationResponse);
        }
        return certificationDTOList;
    }

    public List<ExperienceDTO> addExperience(UserProfile userProfile, List<ExperienceDTO> experienceDTOS) throws NotFoundException {

        List<ExperienceDTO> experienceDTOList = new ArrayList<>();
        for(ExperienceDTO e : experienceDTOS) {
            ExperienceDTO experienceResponse = restTemplate.postForObject("http://localhost:8500/experience", e, ExperienceDTO.class);
            Experience experience = modelMapper.map(experienceResponse, Experience.class);
            userProfile.getExperiences().add(experience);
            experienceDTOList.add(experienceResponse);
        }
        return experienceDTOList;
    }


}
