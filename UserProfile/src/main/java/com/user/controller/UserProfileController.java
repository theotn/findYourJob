package com.user.controller;

import com.user.dto.*;
import com.user.exception.BadRequestException;
import com.user.exception.NotFoundException;
import com.user.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userProfile")
@Validated
public class UserProfileController {

    UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public ResponseEntity<UserProfileDTO> createUserProfile(@RequestParam("user") Integer userId) throws BadRequestException {

        UserProfileDTO userProfileDTO = userProfileService.createUserProfile(userId);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

//    @PostMapping("/education")
//    public ResponseEntity<UserProfileDTO> addEducation(@RequestParam("profile") Integer profileId, @RequestBody EducationDTO educationDTO) throws NotFoundException {
//
//        UserProfileDTO userProfileDTO = userProfileService.addEducation(profileId,educationDTO);
//        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/certification")
//    public ResponseEntity<UserProfileDTO> addCertification(@RequestParam("profile") Integer profileId, @RequestBody CertificationDTO certificationDTO) throws NotFoundException {
//
//        UserProfileDTO userProfileDTO = userProfileService.addCertification(profileId, certificationDTO);
//        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/experience")
//    public ResponseEntity<UserProfileDTO> addExperience(@RequestParam("profile") Integer profileId, @RequestBody ExperienceDTO experienceDTO) throws NotFoundException {
//
//        UserProfileDTO userProfileDTO = userProfileService.addExperience(profileId, experienceDTO);
//        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
//    }
}
