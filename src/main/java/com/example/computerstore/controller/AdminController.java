package com.example.computerstore.controller;

import com.example.computerstore.Payload.LoginPayload;
import com.example.computerstore.model.User;
import com.example.computerstore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String AdminHome(Model model) {
        return "Admin/AdminHome/index";
    }

    @GetMapping("/login")
    public String AdminLogin(Model model) {
        model.addAttribute("login", new LoginPayload());
        return "Admin/Login/login";
    }

    @PostMapping("login")
    public String postAdminLogin(Model model,
                                 @ModelAttribute LoginPayload loginPayload,
                                 HttpSession httpSession) {
        Optional<User> user = userService.getUserByUserName(loginPayload.getEmail());
        if (user.isPresent()) {
            User user1 = user.get();
            httpSession.setAttribute("user", user1);
            return "Admin/AdminHome/index";
        } else {
            model.addAttribute("message", "user is not present");
            return "";
        }
    }

    @GetMapping("logout")
    public String logout(Model model) {
        model.addAttribute("login", new LoginPayload());
        return "Admin/Login/login";
    }
}
