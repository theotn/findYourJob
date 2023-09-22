package com.user.service;


import com.user.dto.UserDTO;
import com.user.exception.BadRequestException;
import com.user.exception.NotFoundException;

public interface UserService {

    UserDTO createUser(UserDTO userDTO) throws BadRequestException;

    UserDTO loginUser(UserDTO userDTO) throws NotFoundException;
}
