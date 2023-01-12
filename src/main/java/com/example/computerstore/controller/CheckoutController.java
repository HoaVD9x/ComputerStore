package com.example.computerstore.controller;

import com.example.computerstore.dao.CategoryRepository;
import com.example.computerstore.model.Order;
import com.example.computerstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CheckoutController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("checkout")
    public String checkout(Model model) {
        model.addAttribute("categorys", categoryRepository.findAll());

        List<Order> orderList = orderService.findAll();
        List<Integer> ints = orderList.stream().map(Order::getQuantityOrder).collect(Collectors.toList());
        int sumQuantityOrder = ints.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sum", sumQuantityOrder);

        return "product/checkout";
    }
}
