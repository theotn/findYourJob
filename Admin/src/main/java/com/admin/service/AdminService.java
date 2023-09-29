package com.admin.service;

import com.admin.dto.FeedbackDTO;
import com.admin.dto.UserDTO;
import com.admin.entity.Feedback;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    List<FeedbackDTO> getFeedbackReported();

    UserDTO changeUserStatus(Integer userId, UserDTO userDTO) throws IOException;
}
