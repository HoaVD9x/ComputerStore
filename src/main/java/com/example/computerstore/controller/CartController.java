package com.example.computerstore.controller;

import com.example.computerstore.Payload.OrderPayLoad;
import com.example.computerstore.dao.OrderRepository;
import com.example.computerstore.model.Cart;
import com.example.computerstore.model.Order;
import com.example.computerstore.service.CartService;
import com.example.computerstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CartController {

    @Autowired
    private OrderService orderService;

    @GetMapping("cart")
    public String cart(Model model) {
        List<Order> cartList = orderService.findAll();
        cartList.stream().filter(order -> order.getQuantityOrder() > 0).collect(Collectors.toList());
        if (cartList.isEmpty()) {
            model.addAttribute("massage", "cart is empty");
        } else {
            model.addAttribute("cartLists", cartList);
            List<Integer> listPrice = cartList.stream().map(order -> order.getProduct().getProductPrice() * order.getQuantityOrder()).collect(Collectors.toList());
            int sumPrice = listPrice.stream().reduce(0, (a, b) -> a + b);
            model.addAttribute("sumPrice", sumPrice);
        }
        model.addAttribute("order",new OrderPayLoad());
        return "product/cart";
    }
}
