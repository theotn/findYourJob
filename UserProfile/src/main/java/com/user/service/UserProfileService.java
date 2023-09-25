package com.user.service;

import com.user.dto.UserDTO;
import com.user.dto.UserProfileDTO;
import com.user.entity.User;
import com.user.entity.UserProfile;
import com.user.exception.BadRequestException;

public interface UserProfileService {

    UserProfileDTO createUserProfile(UserDTO userDTO) throws BadRequestException;
}
