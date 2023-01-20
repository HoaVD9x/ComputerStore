package com.example.computerstore.service;

import com.example.computerstore.Payload.RegisterPayload;
import com.example.computerstore.dao.UserRepositoty;
import com.example.computerstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceIplm implements UserService {

    @Autowired
    private UserRepositoty userRepositoty;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Optional<User> getUserByUserId(int userId) {
        return userRepositoty.getUserByUserId(userId);
    }

    @Override
    public void deleteUser(User user) {
        userRepositoty.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepositoty.findAllByActiveTrue();
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        return userRepositoty.getUserByEmailAndActiveTrue(userName);
    }

    @Override
    public void saveUser(RegisterPayload registerPayload) {
        User user = new User();
        user.setUserId(registerPayload.getUserId());
        user.setFirstName(registerPayload.getFirstName());
        user.setLastName(registerPayload.getLastName());
        user.setUserName(registerPayload.getUserName());
        user.setPassword(bCryptPasswordEncoder.encode(registerPayload.getPassword()));
        user.setPhone(registerPayload.getPhone());
        user.setEmail(registerPayload.getEmail());
        userRepositoty.save(user);
    }

    @Override
    public Optional<User> checkEmail(String email) {
        return userRepositoty.getUserByEmailAndActiveTrue(email);
    }


    @Override
    public void upDateUser(User user) {

        Optional<User> userOptional = userRepositoty.getUserByUserId(user.getUserId());

        if (userOptional.isPresent()) {

            if (StringUtils.isEmpty(user.getPassword())) {
                user.setPassword(userOptional.get().getPassword());

            } else {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
        }
//        if (user.getPassword()== null) {
//            user.setPassword("123");
//        }
        User user1 = userOptional.get();
        user1.setUserId(user.getUserId());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setUserName(user.getUserName());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());
        user1.setPhone(user.getPhone());
        user1.setRole(user.getRole());

        userRepositoty.save(user1);
     }
}
