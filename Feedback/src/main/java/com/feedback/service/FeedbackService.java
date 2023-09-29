package com.feedback.service;

import com.feedback.dto.FeedbackDTO;
import com.feedback.exception.NotFoundException;

import java.util.List;

public interface FeedbackService {

    FeedbackDTO createFeedback(Integer userId, FeedbackDTO feedbackDTO);
    FeedbackDTO getFeedback(Integer feedbackId) throws NotFoundException;
    List<FeedbackDTO> getAllFeedbackReported();
    FeedbackDTO reportFeedback(Integer feedbackId, Integer userId) throws NotFoundException;

}
