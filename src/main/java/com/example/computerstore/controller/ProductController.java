package com.example.computerstore.controller;

import com.example.computerstore.Payload.ProductPayload;
import com.example.computerstore.dao.CategoryRepository;
import com.example.computerstore.model.Products;
import com.example.computerstore.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("newProduct")
    public String newProduct(Model model) {
        model.addAttribute("product", new ProductPayload());
        model.addAttribute("categorys", categoryRepository.findAll());
        return "product/newProduct";
    }

    @GetMapping("products")
    public String AllProduct (Model model) {

        List<Products> productsList = productService.getAll();
        model.addAttribute("categorys",categoryRepository.findAll());
        int min = Collections.min(productsList.stream().map(Products::getProductPrice).collect(Collectors.toList()));
        model.addAttribute("products",productsList);

       List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands",brandList);
//        List<Category>categories = productService.getAll().stream().map(products -> products.getCategory()).distinct().collect(Collectors.toList());

        return "product/products";
    }

    @GetMapping("detail/{productId}")
    public ModelAndView detailProduct(@PathVariable("productId") int productId, Model model) {
        List<Products> productsList = productService.getAll();
        model.addAttribute("categorys",categoryRepository.findAll());
        ProductPayload productPayload = new ProductPayload();
       productPayload.setProductId(productId);
        model.addAttribute("product",productPayload);
        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands",brandList);
        return new ModelAndView("product/newProduct");
    }

    @GetMapping("categoryId/{categoryId}")
    public String getProductByCategoryId (@PathVariable("categoryId") int categoryId, Model model) {
        List<Products> productsList = productService.getAll();
        model.addAttribute("categorys",categoryRepository.findAll());
        model.addAttribute("products", productService.getProductByCategoryId(categoryId));
        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands",brandList);
        return "product/products";
    }

    @GetMapping("brand/{brand}")
    public  String getProductByBrand(@PathVariable("brand") String brand, Model model) {
        List<Products> productsList = productService.getAll();
        model.addAttribute("categorys", categoryRepository.findAll());
        model.addAttribute("products", productService.getProductbyBrand(brand));
        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands",brandList);
        return "product/products";
    }

    @PostMapping("saveProduct")
    public String saveProduct(@ModelAttribute ProductPayload productPayload, HttpServletRequest request, Model model) throws IOException {
        productService.save(productPayload, request);
        String message = "success !";
        model.addAttribute("message",message);

        return "product/newProduct";
    }



}
