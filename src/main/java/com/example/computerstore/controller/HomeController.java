package com.example.computerstore.controller;

import com.example.computerstore.dao.CategoryRepository;
import com.example.computerstore.model.Order;
import com.example.computerstore.model.Products;
import com.example.computerstore.service.OrderService;
import com.example.computerstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping("/")
     public String home (Model model) {

        List<Products> listProducts = productService.findAllProductByBrand( "apple");
        model.addAttribute("brands",listProducts);

        List<Products> Allproducts = productService.findAllProductByBrand("dell");
        model.addAttribute("allProduct",Allproducts);

        List<Products> list = productService.findAllProductByBrand("Android 12");
        model.addAttribute("bestSeller",list);

        List<Order> orderList = orderService.findAll();
        List<Integer> ints = orderList.stream().map(Order::getQuantityOrder).collect(Collectors.toList());
        int sumQuantityOrder = ints.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sum",sumQuantityOrder);

        model.addAttribute("categorys", categoryRepository.findAll());
        return "product/index";}
}
