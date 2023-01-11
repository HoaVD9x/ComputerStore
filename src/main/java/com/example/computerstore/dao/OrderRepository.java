package com.example.computerstore.dao;

import com.example.computerstore.Payload.OrderPayLoad;
import com.example.computerstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface OrderRepository extends JpaRepository<Order, Integer> {
    void save (OrderPayLoad orderPayLoad);

    Order findOrderByOrderId(int orderId);

    void deleteOrderByOrderId(int orderId);
}
