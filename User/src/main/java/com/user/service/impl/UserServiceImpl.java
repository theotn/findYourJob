package com.user.service.impl;

import com.user.dto.FeedbackDTO;
import com.user.dto.UserDTO;
import com.user.entity.User;
import com.user.enums.Role;
import com.user.exception.BadRequestException;
import com.user.exception.NotFoundException;
import com.user.repository.UserRepository;
import com.user.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;

    private RestTemplate restTemplate;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
    }

    //DE MODIFICAT PT ACTIVARE
    @Override
    public UserDTO createUser(UserDTO userDTO) throws BadRequestException {

        if(userDTO.getEmail()==null || userDTO.getEmail().isEmpty() || userDTO.getPassword()==null || userDTO.getPassword().isEmpty()) {

            throw new BadRequestException("Please provide credentials!");
        }
        if(userDTO.getPassword().matches("(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}")) {
            throw new BadRequestException("Password must contain at least: one uppercase letter, one lowercase letter, one digit, one special character and minimum");
        }
        if(userRepository.existsByEmail(userDTO.getEmail())){

            throw new BadRequestException("This email is already used!");
        }


        User user = new User();

        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setIsActive(true);

        if (userDTO.getRole()== Role.USER){

            user.setRole(Role.USER);
            userRepository.save(user);

        }else {

            user.setRole(Role.COMPANY);
            userRepository.save(user);
        }


        UserDTO userCreated = modelMapper.map(user,UserDTO.class);
        userCreated.setPassword(null);

    return userCreated;
    }

    //DE MODIFICAT
    @Override
    public UserDTO loginUser(UserDTO userDTO) throws NotFoundException, BadRequestException {

        if(userDTO.getEmail()==null || userDTO.getEmail().isEmpty() || userDTO.getPassword()==null || userDTO.getPassword().isEmpty()) {

            throw new BadRequestException("Please provide credentials!");
        }

        User userRepo = userRepository.findByEmail(userDTO.getEmail());

        if(userRepo!=null) {
            if (passwordEncoder.matches(userDTO.getPassword(), userRepo.getPassword())) {

                return modelMapper.map(userRepo,UserDTO.class);
            }
        }

       throw new NotFoundException("The credentials provided are incorrect!");

    }
    //DE MODIFICAT
    @Override
    public UserDTO getUser(Integer userId) throws NotFoundException {

        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(()-> new NotFoundException("User not found!"));

        return modelMapper.map(user,UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Integer userId, UserDTO userDTO) throws NotFoundException, BadRequestException {

        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(()-> new NotFoundException("User not found!"));

        if(userDTO.getEmail()!=null){

            if (!userRepository.existsByEmail(userDTO.getEmail())) {
                user.setEmail(userDTO.getEmail());
            }else{
                throw new BadRequestException("This email is already used!");
            }

        }

        if(userDTO.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        return modelMapper.map(user,UserDTO.class);
    }

    //DE MODIFICAT
    @Override
    public UserDTO deleteUser(Integer userId) throws NotFoundException {

        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(()-> new NotFoundException("User not found!"));

        userRepository.delete(user);

        return modelMapper.map(user,UserDTO.class);
    }

    @Override
    public void reportFeedback(Integer userId, Integer feedbackId) throws NotFoundException {

        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(()-> new NotFoundException("User not found!"));

        Map<String, Integer> params = new HashMap<>();
        params.put("feedback", feedbackId);
        params.put("user", userId);
        FeedbackDTO feedbackDTO = restTemplate.postForObject("http://localhost:9200/feedback/report?feedback={feedback}&user={user}", new FeedbackDTO(), FeedbackDTO.class, params);

    }
}
