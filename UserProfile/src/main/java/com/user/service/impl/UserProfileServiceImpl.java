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
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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
}
