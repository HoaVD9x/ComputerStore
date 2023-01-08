package com.example.computerstore.controller;

import com.example.computerstore.Payload.ProductPayload;
import com.example.computerstore.dao.CategoryRepository;
import com.example.computerstore.dao.ProductRepository;
import com.example.computerstore.model.Products;
import com.example.computerstore.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("newProduct")
    public ModelAndView newProduct(ModelMap modelMap) {

        modelMap.addAttribute("product", new ProductPayload());
        modelMap.addAttribute("categorys", categoryRepository.findAll());
        return new ModelAndView("product/newProduct", modelMap);
    }

    @GetMapping("products")
    public String AllProduct(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Products> productsPage = productRepository.findAll(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", productsPage);
        model.addAttribute("categorys", categoryRepository.findAll());
        List<Products> productsList = productService.findAll();
        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);

        return "product/products";
    }

    @GetMapping("detail/{productId}")
    public ModelAndView detailProduct(@PathVariable("productId") int productId, ProductPayload productPayload, Model model) {
        Products product = productService.getProductById(productId);
        String massage = "Edit Product " + product.getProductName();
        List<Products> productsList = productService.findAll();
        model.addAttribute("categorys", categoryRepository.findAll());
        model.addAttribute("product", product);
        model.addAttribute("message", massage);
        productPayload.setProductId(productId);
        return new ModelAndView("product/newProduct", "product", productPayload);
    }

    //
    @GetMapping("categoryId/{categoryId}")
    public String getProductByCategoryId(@PathVariable("categoryId") int categoryId, Model model) {
        List<Products> productsList = productService.findAll();
        model.addAttribute("categorys", categoryRepository.findAll());
        model.addAttribute("products", productService.getProductByCategoryId(categoryId));
        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);
        return "product/products";
    }

    @GetMapping("brand/{brand}")
    public String getProductByBrand(@PathVariable("brand") String brand, Model model) {
        List<Products> productsList = productService.findAll();
        model.addAttribute("categorys", categoryRepository.findAll());
        model.addAttribute("products", productService.getProductbyBrand(brand));
        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);
        return "product/products";
    }

    //
    @PostMapping("saveProduct")
    public ModelAndView saveProduct(@ModelAttribute ProductPayload productPayload,
                                    HttpServletRequest request,
                                    ModelMap modelMap,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) throws IOException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Products> productsPage = productRepository.findAll(PageRequest.of(currentPage - 1, pageSize));
        modelMap.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }

        modelMap.addAttribute("products", productsPage);
        modelMap.addAttribute("categorys", categoryRepository.findAll());
        productService.save(productPayload, request);
        String message = "success save product" + productPayload.getProductName() + " !";
        modelMap.addAttribute("message", message);
        return new ModelAndView("product/products", modelMap);


    }
}
