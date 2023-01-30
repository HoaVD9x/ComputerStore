package com.example.computerstore.controller;

import com.example.computerstore.Payload.LoginPayload;
import com.example.computerstore.dao.CategoryRepository;

import com.example.computerstore.model.OrderDetail;
import com.example.computerstore.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {
    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("checkout")
    public String checkout(Model model,
                           HttpServletRequest request,
                           HttpSession httpSession) {
        OrderDetail orderDetail = new OrderDetail();

        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            model.addAttribute("login" , new LoginPayload());
            return "LoginRegister/login";
        }


        return "";
    }
}
