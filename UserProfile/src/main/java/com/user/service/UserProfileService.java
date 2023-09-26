package com.user.service;

import com.user.dto.CertificationDTO;
import com.user.dto.EducationDTO;
import com.user.dto.UserDTO;
import com.user.dto.UserProfileDTO;
import com.user.entity.Education;
import com.user.entity.User;
import com.user.entity.UserProfile;
import com.user.exception.BadRequestException;
import com.user.exception.NotFoundException;

import java.util.List;

public interface UserProfileService {

    UserProfileDTO createUserProfile(Integer userId) throws BadRequestException;
    UserProfileDTO updateUserProfile(Integer userProfileId, UserProfileDTO userProfileDTO) throws NotFoundException;
//    UserProfileDTO addEducation(List<EducationDTO> educationDTO) throws NotFoundException;

//    UserProfileDTO addCertification(Integer profileId, CertificationDTO certificationDTO) throws NotFoundException;



}
