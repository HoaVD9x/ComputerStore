package com.example.computerstore.dao;

import com.example.computerstore.model.Cart;
import com.example.computerstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CartRepository extends JpaRepository<Cart, Integer> {
    void save(Order order);
}
