package com.example.computerstore.dao;

import com.example.computerstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepositoty  extends JpaRepository<User, Integer> {


    List<User> findAllByActiveTrue();

    Optional<User> getUserByEmailAndActiveTrue(String email);


    Optional<User> getUserByUserId(int userId);


}
