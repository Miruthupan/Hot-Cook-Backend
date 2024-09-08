package com.resturent.resturen_spring.service.auth;

import com.resturent.resturen_spring.dtos.SignupRequest;
import com.resturent.resturen_spring.dtos.UserDto;
import com.resturent.resturen_spring.entities.User;
import com.resturent.resturen_spring.enuma.UserRole;
import com.resturent.resturen_spring.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutoServiceImplementation implements AutoService {

    private final UserRepository userRepository;


    public AutoServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @Override
    public UserDto createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user);
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(createdUser.getId());
        createdUserDto.setName(createdUser.getName());


        createdUserDto.setEmail(createdUser.getEmail());
        createdUserDto.setUserRole(createdUser.getUserRole());


        return createdUserDto;

    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto login(SignupRequest signupRequest) throws Exception {
        User user = userRepository.findByEmail(signupRequest.getEmail());

        if (user == null) {
            throw new Exception("User not found.");
        }

        System.out.println("Retrieved Password: " + user.getPassword());

        if (!passwordEncoder.matches(signupRequest.getPassword(), user.getPassword())) {
            throw new Exception("Invalid credentials.");
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setUserRole(user.getUserRole());

        return userDto;
    }
}
