package com.example.computerstore.controller;

import com.example.computerstore.model.Products;
import com.example.computerstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {



    @GetMapping("/")
     public String home () {return "product/index";}
}
