package com.user.controller;

import com.user.dto.UserDTO;
import com.user.dto.UserProfileDTO;
import com.user.exception.BadRequestException;
import com.user.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userProfile")
@Validated
public class UserProfileController {

    UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public ResponseEntity<UserProfileDTO> createUserProfile(@RequestBody UserDTO userDTO) throws BadRequestException {

        UserProfileDTO userProfileDTO = userProfileService.createUserProfile(userDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }
}
