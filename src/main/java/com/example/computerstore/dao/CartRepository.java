package com.example.computerstore.dao;

import com.example.computerstore.model.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
//    void save(CartItem order);
}
