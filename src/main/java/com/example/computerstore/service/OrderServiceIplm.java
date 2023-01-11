package com.example.computerstore.service;

import com.example.computerstore.Payload.OrderPayLoad;
import com.example.computerstore.dao.OrderRepository;
import com.example.computerstore.model.Order;
import com.example.computerstore.model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceIplm implements OrderService{

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public void save(OrderPayLoad orderPayLoad) {
        Order order = new Order();
        order.setQuantityOrder(orderPayLoad.getQuantityOrder());
        order.setProduct(orderPayLoad.getProduct());
        orderRepository.save(order);

    }

    @Override
    public Order findOrderBYId(int orderId) {
        return orderRepository.findOrderByOrderId(orderId);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(int orderId) {
        orderRepository.deleteOrderByOrderId(orderId);
    }

    @Override
    public void SaveOrder(Order order) {
        orderRepository.save(order);
    }
}
