package com.admin.service.impl;

import com.admin.dto.FeedbackDTO;
import com.admin.dto.UserDTO;
import com.admin.entity.Feedback;
import com.admin.repository.AdminRepository;
import com.admin.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;
    private ModelMapper modelMapper;
    private RestTemplate restTemplate;

    public AdminServiceImpl(AdminRepository adminRepository, ModelMapper modelMapper, RestTemplate restTemplate) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<FeedbackDTO> getFeedbackReported() {

        FeedbackDTO[] feedbackDTOList = restTemplate.getForObject("http://localhost:9200/feedback/reports", FeedbackDTO[].class);
        List<FeedbackDTO> list =  Arrays.asList(feedbackDTOList);

        return list;
    }
}
