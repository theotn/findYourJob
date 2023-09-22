package com.user.api;

import com.user.dto.UserDTO;
import com.user.exception.BadRequestException;
import com.user.exception.NotFoundException;
import com.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) throws BadRequestException {

        UserDTO user = userService.createUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody UserDTO userDTO) throws BadRequestException, NotFoundException {

        UserDTO user = userService.loginUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
