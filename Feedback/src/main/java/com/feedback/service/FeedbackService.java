package com.feedback.service;

import com.feedback.dto.FeedbackDTO;
import com.feedback.exception.NotFoundException;

public interface FeedbackService {

    FeedbackDTO createFeedback(Integer userId, FeedbackDTO feedbackDTO);
    FeedbackDTO getFeedback(Integer feedbackId) throws NotFoundException;
}
