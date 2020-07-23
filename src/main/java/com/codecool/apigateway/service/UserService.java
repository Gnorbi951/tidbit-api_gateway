package com.codecool.apigateway.service;

import com.codecool.apigateway.entity.UserEntity;
import com.codecool.apigateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public List<UserEntity> getAllUser() {
        return repository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return repository.getUserById(id);
    }

    public String getPasswordById(Long id) {
        return repository.getPasswordById(id);
    }

    public Long getIdByName(String name){return repository.getIdByName(name);}
}
