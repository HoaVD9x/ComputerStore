package com.example.computerstore.service;

import com.example.computerstore.Payload.LoginPayload;
import com.example.computerstore.dao.UserRepositoty;

import com.example.computerstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceIplm implements LoginService {

    @Autowired
    private UserRepositoty userRepositoty;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User login(LoginPayload loginPayload) {
        Optional<User> userOptional = userRepositoty.getUserByEmailAndActiveTrue(loginPayload.getEmail());
        if (userOptional.isPresent() && bCryptPasswordEncoder.matches(loginPayload.getPassword(), userOptional.get().getPassword())) {
            userOptional.get().setPassword("");
            return userOptional.get();
        }
        return null;
    }
}
