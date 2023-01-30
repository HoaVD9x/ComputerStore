package com.example.computerstore.controller;

import com.example.computerstore.Payload.LoginPayload;
import com.example.computerstore.Payload.RegisterPayload;
import com.example.computerstore.dao.RoleRepository;
import com.example.computerstore.model.Role;
import com.example.computerstore.model.User;
import com.example.computerstore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;


    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("")
    public String AllUser(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("message", "");
        return "Admin/User/listUser1";
    }

    @GetMapping("newUser")
    public String newUser(Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new RegisterPayload());
        return "Admin/User/userDetail1";
    }




    @GetMapping("detail")
    public String userDetail(Model model,
                             @RequestParam("userId") int userId) {

        Optional<User> userOptional = userService.getUserByUserId(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            model.addAttribute("message", "edit User : " + user.getUserName());
           model.addAttribute("user", user);
            List<Role> roles = roleRepository.findAll();
            model.addAttribute("roles", roles);
            return "Admin/User/userDetail1";

        } else {
            model.addAttribute("message", "user is not present");
            return "redirect:/admin/user/";
        }
    }




    @GetMapping("delete")
    public String deleteUser(@RequestParam("userId") int userId, Model model){
        Optional<User> userOptional =  userService.getUserByUserId(userId);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setActive(false);
            userService.deleteUser(user);
            model.addAttribute("message","user : " + userOptional.get().getUserName() + " is updated !");
        } else {
            model.addAttribute("message","user : " + userId + " is not present !" );

        }
        return "redirect:/admin/user";
    }

    @PostMapping("save")
    public String saveUser(@ModelAttribute User user,
                           Model model) {
        userService.upDateUser(user);
        model.addAttribute("message", "member successfully saved !");

        return "redirect:/admin/user";
    }






}
