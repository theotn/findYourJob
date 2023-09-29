package com.feedback.api;

import com.feedback.dto.FeedbackDTO;
import com.feedback.entity.Feedback;
import com.feedback.exception.NotFoundException;
import com.feedback.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackDTO> createFeedback(@RequestParam("user") Integer userId, @RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO feedback = feedbackService.createFeedback(userId, feedbackDTO);
        return new ResponseEntity<>(feedback, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<FeedbackDTO> getFeedback(@RequestParam("feedback") Integer feedbackId) throws NotFoundException {
        FeedbackDTO feedback = feedbackService.getFeedback(feedbackId);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }
    @GetMapping("/reports")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbackReported() {
        List<FeedbackDTO> feedbackList = feedbackService.getAllFeedbackReported();
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }
    @PostMapping("/report")
    public ResponseEntity<FeedbackDTO> reportFeedback(@RequestParam("feedback") Integer feedbackId, @RequestParam("user") Integer userId) throws NotFoundException {
        FeedbackDTO feedback = feedbackService.reportFeedback(feedbackId, userId);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

}
