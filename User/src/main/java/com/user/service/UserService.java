package com.user.service;


import com.user.dto.UserDTO;
import com.user.entity.User;
import com.user.exception.BadRequestException;
import com.user.exception.NotFoundException;

public interface UserService {

    UserDTO createUser(UserDTO userDTO) throws BadRequestException;
    UserDTO loginUser(UserDTO userDTO) throws NotFoundException, BadRequestException;
    UserDTO getUser(Integer userId) throws NotFoundException;
    UserDTO updateUser(Integer userId, UserDTO userDTO) throws NotFoundException, BadRequestException;
    UserDTO deleteUser(Integer userId) throws NotFoundException;
    void reportFeedback(Integer userId, Integer feedbackId) throws NotFoundException;

}
