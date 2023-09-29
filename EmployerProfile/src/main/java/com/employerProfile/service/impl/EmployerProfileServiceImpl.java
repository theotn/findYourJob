package com.employerProfile.service.impl;

import com.employerProfile.dto.EmployerProfileDTO;
import com.employerProfile.dto.FeedbackDTO;
import com.employerProfile.dto.UserDTO;
import com.employerProfile.entity.EmployerProfile;
import com.employerProfile.entity.Feedback;
import com.employerProfile.entity.User;
import com.employerProfile.exception.NotFoundException;
import com.employerProfile.repository.EmployerProfileRepository;
import com.employerProfile.service.EmployerProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.print.DocFlavor;
import java.util.*;

@Service
@Transactional
public class EmployerProfileServiceImpl implements EmployerProfileService {

    private EmployerProfileRepository employerProfileRepository;
    private RestTemplate restTemplate;
    private ModelMapper modelMapper;

    public EmployerProfileServiceImpl(EmployerProfileRepository employerProfileRepository, RestTemplate restTemplate, ModelMapper modelMapper) {
        this.employerProfileRepository = employerProfileRepository;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployerProfileDTO createEmployerProfile(Integer userId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("user", userId);

        User user = restTemplate.getForObject("http://localhost:8000/user?user={user}", User.class, params);

        EmployerProfile employerProfile = new EmployerProfile();
        employerProfile.setUser(user);

        employerProfileRepository.save(employerProfile);

        return modelMapper.map(employerProfile, EmployerProfileDTO.class);
    }

    @Override
    public EmployerProfileDTO getEmployerProfile(Integer userId) throws NotFoundException {

        Map<String, Integer> params = new HashMap<>();
        params.put("user", userId);
        UserDTO userDTO = restTemplate.getForObject("http://localhost:8000/user?user={user}", UserDTO.class, params);

        EmployerProfile employerProfile = employerProfileRepository.findByUser(modelMapper.map(userDTO, User.class));

        if(employerProfile == null) throw new NotFoundException("User not found!");

        List<FeedbackDTO> feedbackDTOList = new ArrayList<>();

        for(Feedback f : employerProfile.getFeedback()) {
            FeedbackDTO feedbackDTO = modelMapper.map(f, FeedbackDTO.class);
            feedbackDTOList.add(feedbackDTO);
        }

        EmployerProfileDTO employerProfileDTO = modelMapper.map(employerProfile, EmployerProfileDTO.class);
        employerProfileDTO.setFeedbackDTOList(feedbackDTOList);

        return employerProfileDTO;
    }

    @Override
    public EmployerProfileDTO updateEmployerProfile(Integer employerProfileId, EmployerProfileDTO employerProfileDTO) throws NotFoundException {

        Optional<EmployerProfile> employerProfileOptional = employerProfileRepository.findById(employerProfileId);
        EmployerProfile employerProfile = employerProfileOptional.orElseThrow(() -> new NotFoundException("Profile not found!"));

        if(employerProfileDTO.getName() != null) employerProfile.setName(employerProfileDTO.getName());
        if(employerProfileDTO.getHeadquarter() != null) employerProfile.setHeadquarter(employerProfileDTO.getHeadquarter());
        if(employerProfileDTO.getDomain() != null) employerProfile.setDomain(employerProfileDTO.getDomain());
        if(employerProfileDTO.getDescription() != null) employerProfile.setDescription(employerProfileDTO.getDescription());
        if(employerProfileDTO.getNoOfEmployees() != null) employerProfile.setNoOfEmployees(employerProfileDTO.getNoOfEmployees());

        return modelMapper.map(employerProfile, EmployerProfileDTO.class);
    }

    @Override
    public FeedbackDTO addFeedbackToProfile(Integer userId, Integer employerProfileId, FeedbackDTO feedbackDTO) throws NotFoundException {

        Optional<EmployerProfile> employerProfileOptional = employerProfileRepository.findById(employerProfileId);
        EmployerProfile employerProfile = employerProfileOptional.orElseThrow(() -> new NotFoundException("Profile not found!"));

        Map<String, Integer> params = new HashMap<>();
        params.put("user", userId);
        FeedbackDTO feedback = restTemplate.postForObject("http://localhost:9200/feedback?user={user}", feedbackDTO, FeedbackDTO.class, params);
        employerProfile.getFeedback().add(modelMapper.map(feedback, Feedback.class));

        return feedback;
    }
}
