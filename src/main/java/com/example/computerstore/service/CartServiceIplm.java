package com.example.computerstore.service;

import com.example.computerstore.dao.CartRepository;
import com.example.computerstore.model.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceIplm implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

//    @Override
//    public void save(CartItem order) {
//        cartRepository.save(order);
//    }
}
