package com.feedback.service.impl;

import com.feedback.dto.FeedbackDTO;
import com.feedback.dto.UserDTO;
import com.feedback.entity.Feedback;
import com.feedback.entity.User;
import com.feedback.exception.NotFoundException;
import com.feedback.repository.FeedbackRepository;
import com.feedback.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackRepository feedbackRepository;
    private RestTemplate restTemplate;
    private ModelMapper modelMapper;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, RestTemplate restTemplate, ModelMapper modelMapper) {
        this.feedbackRepository = feedbackRepository;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public FeedbackDTO createFeedback(Integer userId, FeedbackDTO feedbackDTO) {

        Feedback feedback = modelMapper.map(feedbackDTO, Feedback.class);

        Map<String, Integer> params = new HashMap<>();
        params.put("user", userId);
        UserDTO userDTO = restTemplate.getForObject("http://localhost:8000/user?user={user}", UserDTO.class, params);
        User user = modelMapper.map(userDTO, User.class);

        feedback.setReports(0);
        feedback.setUser(user);
        feedbackRepository.save(feedback);

        FeedbackDTO feedbackReturned = modelMapper.map(feedback, FeedbackDTO.class);
        feedbackReturned.setUserDTO(userDTO);


        return feedbackReturned;
    }

    @Override
    public FeedbackDTO getFeedback(Integer feedbackId) throws NotFoundException {

        Optional<Feedback> feedbackOptional = feedbackRepository.findById(feedbackId);
        Feedback feedback = feedbackOptional.orElseThrow(() -> new NotFoundException("Not found!"));

        return modelMapper.map(feedback, FeedbackDTO.class);
    }
}
