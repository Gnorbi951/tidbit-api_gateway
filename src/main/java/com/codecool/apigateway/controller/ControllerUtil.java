package com.codecool.apigateway.controller;

import com.codecool.apigateway.entity.UserEntity;
import com.codecool.apigateway.repository.UserRepository;
import com.codecool.apigateway.security.JwtTokenServices;
import org.springframework.stereotype.Component;

@Component
public class ControllerUtil {
    UserRepository userRepository;
    JwtTokenServices jwtTokenServices;

    public ControllerUtil(UserRepository userRepository, JwtTokenServices jwtTokenServices) {
        this.userRepository = userRepository;
        this.jwtTokenServices = jwtTokenServices;
    }

    public UserEntity getUserFromToken(String authorizationString) {
        String token = jwtTokenServices.getTokenFromRequestHeaderAuthorization(authorizationString);
        String loggedInUserName = jwtTokenServices.getUsernameFromToken(token);
        return userRepository.getUserEntityByName(loggedInUserName);
    }

}
