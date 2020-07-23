package com.codecool.apigateway.service;

import com.codecool.apigateway.entity.UserEntity;
import com.codecool.apigateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public String registerUser(UserEntity data) {
        String userName = data.getName();
        String password = data.getPassword();
        String email = data.getEmail();

        // Linked list validation chain with booleans

        // First check for invalid input
        char[] illegalCharacterList = {'.', ',', '+', '-', '[', ']', '{', '}', ' ', '"', '\'', '/', '\\'};
        if (userName == null || userName.length() < 4) return "Username is too short";
        if (userName.length() > 20) return "Username is too long";
        if (password == null || password.length() < 4) return "Password is too short";
        if (password.length() > 20) return "Password is too long";
        if (Character.isDigit(userName.charAt(0))) return "First character can't be a number";

        for (char c : illegalCharacterList) {
            for (int i=0; i<userName.length(); i++) {
                if (userName.charAt(i) == c) return "Username contains illegal characters";
            }
        }

        List<UserEntity> userList = userRepository.findAll();
        for (UserEntity hotelUser : userList) {
            if (hotelUser.getName().equals(userName)) {
                return "Username already taken";
            }
        }

        UserEntity user = UserEntity.builder()
                .name(userName)
                .password(passwordEncoder.encode(password))
                .email(email)
                .roles(Collections.singletonList("USER"))
                .build();

        userRepository.save(user);

        return "Successful Registration";

    }
}