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

import java.util.*;

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
        feedbackReturned.setUser(userDTO);

        return feedbackReturned;
    }

    @Override
    public FeedbackDTO getFeedback(Integer feedbackId) throws NotFoundException {

        Optional<Feedback> feedbackOptional = feedbackRepository.findById(feedbackId);
        Feedback feedback = feedbackOptional.orElseThrow(() -> new NotFoundException("Not found!"));

        return modelMapper.map(feedback, FeedbackDTO.class);
    }

    @Override
    public List<FeedbackDTO> getAllFeedbackReported() {

        List<Feedback> feedbackList = feedbackRepository.getAllFeedbackReported();

        List<FeedbackDTO> feedbackDTOList = new ArrayList<>();

        for (Feedback f : feedbackList) {
            FeedbackDTO feedbackDTO = modelMapper.map(f, FeedbackDTO.class);
            //   feedbackDTO.setUserReport(f.getUserReport());

            User userPost = f.getUser();
            feedbackDTO.setUser(modelMapper.map(userPost, UserDTO.class));

            List<UserDTO> userDTOS = new ArrayList<>();

            for (Integer i : f.getUserReport()) {

                UserDTO userDTO = restTemplate.getForObject("http://localhost:8000/user?user=" + i, UserDTO.class);
                userDTOS.add(userDTO);
            }
            feedbackDTO.setUserReportList(userDTOS);

            feedbackDTOList.add(feedbackDTO);
        }

        return feedbackDTOList;
    }

    @Override
    public FeedbackDTO reportFeedback(Integer feedbackId, Integer userId) throws NotFoundException {

        Optional<Feedback> feedbackOptional = feedbackRepository.findById(feedbackId);
        Feedback feedback = feedbackOptional.orElseThrow(() -> new NotFoundException("Not found!"));

        feedback.setReports(feedback.getReports() + 1);

        if (feedback.getUserReport().contains(userId))
            throw new NotFoundException("This feedback has already been reported!");
        else feedback.getUserReport().add(userId);

        return modelMapper.map(feedback, FeedbackDTO.class);
    }
}
