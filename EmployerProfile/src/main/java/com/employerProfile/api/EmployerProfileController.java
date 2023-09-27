package com.employerProfile.api;

import com.employerProfile.dto.EmployerProfileDTO;
import com.employerProfile.dto.FeedbackDTO;
import com.employerProfile.exception.NotFoundException;
import com.employerProfile.service.EmployerProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employerProfile")
public class EmployerProfileController {

    private EmployerProfileService employerProfileService;

    public EmployerProfileController(EmployerProfileService employerProfileService) {
        this.employerProfileService = employerProfileService;
    }

    @PostMapping
    public ResponseEntity<EmployerProfileDTO> createEmployerProfile(@RequestParam("user") Integer userId) {
        EmployerProfileDTO employerProfileDTO = employerProfileService.createEmployerProfile(userId);
        return new ResponseEntity<>(employerProfileDTO, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<EmployerProfileDTO> getEmployerProfile(@RequestParam("user") Integer userId) throws NotFoundException {
        EmployerProfileDTO employerProfileDTO = employerProfileService.getEmployerProfile(userId);
        return new ResponseEntity<>(employerProfileDTO, HttpStatus.OK);
    }
    @PatchMapping
    public ResponseEntity<EmployerProfileDTO> updateEmployerProfile(@RequestParam("profile") Integer profileId, @RequestBody EmployerProfileDTO employerProfileDTO) throws NotFoundException {
        EmployerProfileDTO employerProfile = employerProfileService.updateEmployerProfile(profileId, employerProfileDTO);
        return new ResponseEntity<>(employerProfile, HttpStatus.OK);
    }
    @PostMapping("/feedback")
    public ResponseEntity<FeedbackDTO> addFeedbackToProfile(@RequestParam("user") Integer userId, @RequestParam("profile") Integer profileId, @RequestBody FeedbackDTO feedbackDTO) throws NotFoundException {
        FeedbackDTO feedback = employerProfileService.addFeedbackToProfile(userId, profileId, feedbackDTO);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }
}
