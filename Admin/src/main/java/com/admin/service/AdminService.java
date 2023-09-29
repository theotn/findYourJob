package com.admin.service;

import com.admin.dto.FeedbackDTO;
import com.admin.entity.Feedback;

import java.util.List;

public interface AdminService {

    List<FeedbackDTO> getFeedbackReported();
}
