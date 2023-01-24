package com.example.computerstore.controller;

import com.example.computerstore.dao.CategoryRepository;

import com.example.computerstore.model.Cart;
import com.example.computerstore.model.User;
import com.example.computerstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("cart")
    public String cart(Model model) {
        List<Cart> cartList = cartService.findAll();
        if (cartList.isEmpty()) {
            model.addAttribute("message" , "cart is empty");
        }


        model.addAttribute("user", new User());
        model.addAttribute("categorys", categoryRepository.findAll());
        return "product/productCart";
    }
}
