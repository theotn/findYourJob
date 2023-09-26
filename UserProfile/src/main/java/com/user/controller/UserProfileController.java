package com.user.controller;

import com.user.dto.*;
import com.user.exception.BadRequestException;
import com.user.exception.NotFoundException;
import com.user.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/education")
    public ResponseEntity<EducationDTO> addEducationToProfile(@RequestParam("profile") Integer profileId, @RequestBody EducationDTO educationDTO) throws NotFoundException {

        EducationDTO education = userProfileService.addEducationToProfile(profileId, educationDTO);
        return new ResponseEntity<>(education, HttpStatus.CREATED);
    }

    @PostMapping("/certification")
    public ResponseEntity<CertificationDTO> addCertificationToProfile(@RequestParam("profile") Integer profileId, @RequestBody CertificationDTO certificationDTO) throws NotFoundException {

        CertificationDTO certification = userProfileService.addCertificationToProfile(profileId, certificationDTO);
        return new ResponseEntity<>(certification, HttpStatus.CREATED);
    }

    @PostMapping("/experience")
    public ResponseEntity<ExperienceDTO> addExperienceToProfile(@RequestParam("profile") Integer profileId, @RequestBody ExperienceDTO experienceDTO) throws NotFoundException {

        ExperienceDTO experience = userProfileService.addExperienceToProfile(profileId, experienceDTO);
        return new ResponseEntity<>(experience, HttpStatus.CREATED);
    }

    @PostMapping("/language")
    public ResponseEntity<LanguageDTO> addLanguageToProfile(@RequestParam("profile") Integer profileId, @RequestBody LanguageDTO languageDTO) throws NotFoundException {

        LanguageDTO language = userProfileService.addLanguageToProfile(profileId, languageDTO);
        return new ResponseEntity<>(language, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<UserProfileDTO> updateUserProfile(@RequestParam("profile") Integer profileId, @RequestBody UserProfileDTO userProfile) throws NotFoundException {
        UserProfileDTO userProfileDTO = userProfileService.updateUserProfile(profileId, userProfile);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
    }

    @DeleteMapping("/education")
    public ResponseEntity<EducationDTO> deleteEducationFromProfile(@RequestParam("profile") Integer profileId, @RequestParam("education")  Integer educationId) throws NotFoundException {

        EducationDTO education = userProfileService.deleteEducationFromProfile(profileId, educationId);
        Map<String,Integer> params = new HashMap<>();
        params.put("education",educationId);

        new RestTemplate().delete("http://localhost:8400/education?education={education}",params);

        return new ResponseEntity<>(education, HttpStatus.OK);
    }

    @DeleteMapping("/certification")
    public ResponseEntity<CertificationDTO> deleteCertificationFromProfile(@RequestParam("profile") Integer profileId, @RequestParam("certification")  Integer certificationId) throws NotFoundException {

        CertificationDTO certification = userProfileService.deleteCertificationFromProfile(profileId, certificationId);
        Map<String,Integer> params = new HashMap<>();
        params.put("certification",certificationId);

        new RestTemplate().delete("http://localhost:8200/certification?certification={certification}",params);

        return new ResponseEntity<>(certification, HttpStatus.OK);
    }


    @DeleteMapping("/experience")
    public ResponseEntity<ExperienceDTO> deleteExperienceFromProfile(@RequestParam("profile") Integer profileId, @RequestParam("experience")  Integer experienceId) throws NotFoundException {

        ExperienceDTO experience = userProfileService.deleteExperienceFromProfile(profileId, experienceId);
        Map<String,Integer> params = new HashMap<>();
        params.put("experience",experienceId);

        new RestTemplate().delete("http://localhost:8500/experience?experience={experience}",params);

        return new ResponseEntity<>(experience, HttpStatus.OK);
    }


    @DeleteMapping("/language")
    public ResponseEntity<LanguageDTO> deleteLanguageFromProfile(@RequestParam("profile") Integer profileId, @RequestParam("language")  Integer languageId) throws NotFoundException {

        LanguageDTO language = userProfileService.deleteLanguageFromProfile(profileId, languageId);
        Map<String,Integer> params = new HashMap<>();
        params.put("language",languageId);
        new RestTemplate().delete("http://localhost:8700/language?language={language}",params);

        return new ResponseEntity<>(language, HttpStatus.OK);
    }


}
