package com.user.service.impl;

import com.user.dto.UserDTO;
import com.user.dto.UserProfileDTO;
import com.user.entity.User;
import com.user.entity.UserProfile;
import com.user.exception.BadRequestException;
import com.user.repository.UserProfileRepository;
import com.user.service.UserProfileService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private UserProfileRepository userProfileRepository;

    private ModelMapper modelMapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, ModelMapper modelMapper) {
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserProfileDTO createUserProfile(UserDTO userDTO) throws BadRequestException {

        if(userDTO==null) throw new BadRequestException("User not found!");

        UserProfile userProfile = new UserProfile();

        User user = modelMapper.map(userDTO,User.class);
        userProfile.setUser(user);

        userProfileRepository.save(userProfile);

        return modelMapper.map(userProfile,UserProfileDTO.class);
    }
}
