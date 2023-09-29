package com.employerProfile.service;

import com.employerProfile.dto.EmployerProfileDTO;
import com.employerProfile.dto.FeedbackDTO;
import com.employerProfile.entity.EmployerProfile;
import com.employerProfile.entity.Feedback;
import com.employerProfile.exception.NotFoundException;

public interface EmployerProfileService {

    EmployerProfileDTO createEmployerProfile(Integer userId);
    EmployerProfileDTO getEmployerProfile(Integer userId) throws NotFoundException;
    EmployerProfileDTO updateEmployerProfile(Integer employerProfileId, EmployerProfileDTO employerProfileDTO) throws NotFoundException;
    FeedbackDTO addFeedbackToProfile(Integer userId, Integer employerProfileId, FeedbackDTO feedbackDTO) throws NotFoundException;

    //de adaugat DEZACTIVAREA
}
