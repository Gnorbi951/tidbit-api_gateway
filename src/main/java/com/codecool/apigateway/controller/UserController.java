package com.codecool.apigateway.controller;

import com.codecool.apigateway.entity.UserEntity;
import com.codecool.apigateway.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userservice")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/user")
    public List<UserEntity> getAllUser(){
        return service.getAllUser();
    }

    @GetMapping("/user/{id}")
    public UserEntity getUserById(@PathVariable("id")Long id){
        return service.getUserById(id);
    }

    public String getPasswordById(@PathVariable("id")Long id){
        return service.getPasswordById(id);
    }
}
