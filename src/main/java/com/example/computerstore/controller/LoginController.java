package com.example.computerstore.controller;

import com.example.computerstore.Payload.LoginPayload;
import com.example.computerstore.Payload.RegisterPayload;
import com.example.computerstore.model.Role;
import com.example.computerstore.model.User;
import com.example.computerstore.service.LoginService;
import com.example.computerstore.service.ProductService;
import com.example.computerstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("login", new LoginPayload());
        return "LoginRegister/login";
    }

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("registerpayload", new RegisterPayload());
        return "LoginRegister/register";
    }

    @GetMapping("userDetail")
    public String userDetail(Model model,
                             @RequestParam("userName") String userName) {
        Optional<User> user = userService.getUserByUserName(userName);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "User/UserDetail";
        }
        model.addAttribute("message", "user is not present");
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("logout")
    public String logout(Model model) {
        model.addAttribute("login", new LoginPayload());
        httpSession.setAttribute("user", null);
        return "LoginRegister/login";
    }

    @GetMapping("deleteUser")
    public String DeleteUser(@RequestParam("userId") int userId,
                             Model model) {
        Optional<User> userOptional = userService.getUserByUserId(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(false);
            userService.upDateUser(user);
            return "LoginRegister/login";
        }
        model.addAttribute("message", "user is not present");
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("login")
    public String Login(Model model,
                        @Valid LoginPayload loginPayload,
                        BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("login", new LoginPayload());
            return "LoginRegister/login";
        }

        User user = loginService.login(loginPayload);
        if (user == null) {
            model.addAttribute("message", "INVALID userName or passWord");
            model.addAttribute("login", new LoginPayload());
            return "LoginRegister/login";
        } else {
            httpSession.setAttribute("user", loginPayload);
            return "redirect:/";
        }
//        if (user.getRole().getRoleId() == 2) {
//            httpSession.setAttribute("user", user);
//            return "redirect:/admin/user/";
//
//        } else {
//            httpSession.setAttribute("user", user);
//
//
//            return "redirect:/";
//        }
    }

    @PostMapping("register")
    public String register(Model model,
                           @ModelAttribute RegisterPayload registerPayload) {
        Optional<User> userOptional = userService.checkEmail(registerPayload.getEmail());
        if (!userOptional.isPresent()) {
            userService.saveUser(registerPayload);
            model.addAttribute("message", "Sign Up Success !");

            return "redirect:/login";
        } else {
            model.addAttribute("message", "email is present ");
            model.addAttribute("registerpayload", new RegisterPayload());
            return "LoginRegister/register";
        }
    }

    @PostMapping("upDateUser")
    public String upDateUser(Model model,
                             @ModelAttribute User user) {
        Optional<User> userOptional = userService.getUserByUserId(user.getUserId());
        if (userOptional.isPresent()) {
            userService.upDateUser(user);

            User user2 = userOptional.get();
            model.addAttribute("user", user2);
            model.addAttribute("message", "user is upDate !");
            return "User/UserDetail";
        } else {
            model.addAttribute("messge", "user is not present");
            return "redirect:/";
        }
    }


}
