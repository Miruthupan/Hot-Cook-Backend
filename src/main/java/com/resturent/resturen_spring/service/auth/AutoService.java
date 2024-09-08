package com.resturent.resturen_spring.service.auth;

import com.resturent.resturen_spring.dtos.SignupRequest;
import com.resturent.resturen_spring.dtos.UserDto;

public interface AutoService {
    UserDto createUser(SignupRequest signupRequest);

    UserDto login(SignupRequest signupRequest) throws Exception;
}
