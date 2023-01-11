package com.example.computerstore.service;

import com.example.computerstore.model.Cart;
import com.example.computerstore.model.Order;
import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap;

import java.util.List;
import java.util.Optional;

public interface CartService {
    List<Cart> findAll();

    void save(Order order);

}
