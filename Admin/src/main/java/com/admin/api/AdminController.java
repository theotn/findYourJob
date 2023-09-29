package com.admin.api;

import com.admin.dto.FeedbackDTO;
import com.admin.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getFeedbackReported(){
        List<FeedbackDTO> feedbackDTOList = adminService.getFeedbackReported();
        return new ResponseEntity<>(feedbackDTOList, HttpStatus.OK);
    }
}
