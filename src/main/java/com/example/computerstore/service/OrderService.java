package com.example.computerstore.service;

import com.example.computerstore.Payload.OrderPayLoad;
import com.example.computerstore.model.Order;
import com.example.computerstore.model.Products;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {

     void save(OrderPayLoad orderPayLoad);

     Order findOrderBYId(int orderId);

     List<Order> findAll();

     void deleteOrder(int orderId);

     void SaveOrder(Order order);

}
