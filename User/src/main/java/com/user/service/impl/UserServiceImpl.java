package com.user.service.impl;

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

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) throws BadRequestException {

        if(userRepository.existsByEmail(userDTO.getEmail())){

            throw new BadRequestException("This email is already used!");
        }

        User user = new User();

        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setIsActive(true);

        if (userDTO.getRole()== Role.USER){

            user.setRole(Role.USER);

        }else user.setRole(Role.COMPANY);

        userRepository.save(user);

        UserDTO userCreated = modelMapper.map(user,UserDTO.class);
        userCreated.setPassword(null);

    return userCreated;
    }

    @Override
    public UserDTO loginUser(UserDTO userDTO) throws NotFoundException {

        User userRepo = userRepository.findByEmail(userDTO.getEmail());

        if(userRepo!=null) {
            if (passwordEncoder.matches(userDTO.getPassword(), userRepo.getPassword())) {

                return modelMapper.map(userRepo,UserDTO.class);
            }
        }

       throw new NotFoundException("There is no user with this credentials!");

    }
}
