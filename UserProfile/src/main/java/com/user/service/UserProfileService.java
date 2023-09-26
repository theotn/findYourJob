package com.user.service;

import com.user.dto.*;
import com.user.entity.Education;
import com.user.entity.User;
import com.user.entity.UserProfile;
import com.user.exception.BadRequestException;
import com.user.exception.NotFoundException;

public interface UserProfileService {

    UserProfileDTO createUserProfile(Integer userId) throws BadRequestException;

    UserProfileDTO addEducation(Integer profileId, EducationDTO educationDTO) throws NotFoundException;

    UserProfileDTO addCertification(Integer profileId, CertificationDTO certificationDTO) throws NotFoundException;



}
