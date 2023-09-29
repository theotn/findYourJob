package com.user.api;

import com.user.dto.UserDTO;
import com.user.entity.User;
import com.user.enums.Role;
import com.user.exception.BadRequestException;
import com.user.exception.NotFoundException;
import com.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private UserService userService;

    private RestTemplate restTemplate;

    public UserController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) throws BadRequestException {

        UserDTO user = userService.createUser(userDTO);

        Map<String,Integer> params = new HashMap<>();
        params.put("user",user.getId());

        if(user.getRole() == Role.USER) {
            restTemplate.postForObject("http://localhost:8100/userProfile?user={user}", new User(),Object.class,params);
        } else if(user.getRole() == Role.COMPANY) {
            restTemplate.postForObject("http://localhost:9100/employerProfile?user={user}", new User(),Object.class,params);
        }

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@Valid @RequestBody UserDTO userDTO) throws BadRequestException, NotFoundException {

        UserDTO user = userService.loginUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser(@RequestParam("user") Integer userId) throws BadRequestException, NotFoundException {

        UserDTO user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<UserDTO> updateUser(@RequestParam("user") Integer userId,@RequestBody UserDTO userDTO) throws BadRequestException, NotFoundException {

        UserDTO user = userService.updateUser(userId,userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<UserDTO> deleteUser(@RequestParam("user") Integer userId) throws NotFoundException {

        UserDTO user = userService.deleteUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/feedback")
    public ResponseEntity<String> reportFeedback(@RequestParam("user") Integer userId, @RequestParam("feedback") Integer feedbackId) throws NotFoundException {

        userService.reportFeedback(userId, feedbackId);
        return new ResponseEntity<>("succes", HttpStatus.OK);
    }

}
