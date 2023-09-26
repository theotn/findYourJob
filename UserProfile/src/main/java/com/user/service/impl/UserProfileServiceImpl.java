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

            userProfile.setSkills(userProfileDTO.getSkills());
        }
        if(!userProfileDTO.getDomains().isEmpty()){

            userProfile.setDomains(userProfileDTO.getDomains());
        }



        UserProfileDTO profile = modelMapper.map(userProfile, UserProfileDTO.class);

        return profile;
    }




}
