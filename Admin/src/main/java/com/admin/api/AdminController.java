package com.admin.api;

import com.admin.dto.FeedbackDTO;
import com.admin.dto.UserDTO;
import com.admin.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @PutMapping("/user-status")
    public ResponseEntity<UserDTO> changeUserStatus(@RequestParam("user") Integer userId, @RequestBody UserDTO userDTO) throws IOException {
        UserDTO user = adminService.changeUserStatus(userId,userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
