package com.example.computerstore.service;

import com.example.computerstore.Payload.RegisterPayload;
import com.example.computerstore.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;


public interface UserService {



    void saveUser(RegisterPayload registerPayload);

    Optional<User> getUserByUserId(int userId);

    void deleteUser(User user);

    List<User> getAll();

    Optional<User> getUserByUserName(String userName);

    void upDateUser(User user);

    Optional<User> checkEmail(String email);


}
