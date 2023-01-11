package com.example.computerstore.controller;

import com.example.computerstore.model.Order;
import com.example.computerstore.model.Products;
import com.example.computerstore.service.CartService;
import com.example.computerstore.service.OrderService;
import com.example.computerstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
     public String home (Model model) {

        List<Products> listProducts = productService.getProductbyBrand( "apple");
        model.addAttribute("brands",listProducts);

        List<Products> Allproducts = productService.getProductbyBrand("dell");
        model.addAttribute("allProduct",Allproducts);

        List<Products> list = productService.getProductbyBrand("Android 12");
        model.addAttribute("bestSeller",list);

        List<Order> orderList = orderService.findAll();
        List<Integer> ints = orderList.stream().map(Order::getQuantityOrder).collect(Collectors.toList());
        int sumQuantityOrder = ints.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sum",sumQuantityOrder);
        return "product/index";}
}
