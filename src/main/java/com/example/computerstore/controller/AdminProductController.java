package com.example.computerstore.controller;

import com.example.computerstore.Payload.ProductPayload;
import com.example.computerstore.dao.CategoryRepository;
import com.example.computerstore.dao.ProductRepository;

import com.example.computerstore.model.Product;

import com.example.computerstore.service.ProductService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/product")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    private String home(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {

        // pageable
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        Page<Product> productsPage = productRepository.getProductsByActiveTrue(PageRequest.of(currentPage - 1, pageSize));
        productsPage.stream().collect(Collectors.toList());


        model.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", productsPage);


        return "Admin/User/index";
    }
    @GetMapping("newProduct")
    public ModelAndView newProduct(ModelMap modelMap) {
        modelMap.addAttribute("categorys", categoryRepository.findAll());
        modelMap.addAttribute("message", "Add New Product !");
        modelMap.addAttribute("payload", new ProductPayload());
        modelMap.addAttribute("products", new Product());

        return new ModelAndView("Admin/product/NewProduct", modelMap);
    }
    @GetMapping("detail")
    public String detailProduct(@RequestParam("productId") int productId,
                                Model model) {
        Optional<Product> productOptional = productService.getProductById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("products", product);

            model.addAttribute("message", "edit Product : " + product.getProductName());
            model.addAttribute("categorys", categoryRepository.findAll());
            model.addAttribute("payload", new ProductPayload());
            return "Admin/product/NewProduct";

        }
        model.addAttribute("message", "Product is not present !");
        return "Admin/User/index";
    }
    @GetMapping("delete")
    public String deleteProduct(@RequestParam("productId") int productId,
                                Model model) {

        Optional<Product> productOptional = productService.getProductById(productId);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            product.setActive(false);
            productService.saveProduct(product);
            model.addAttribute("message", "successful delete " + product.getProductName());

            return "redirect:/admin/product";
        } else {
            model.addAttribute("message", "product is not present !");
            return "redirect:/admin/product/";
        }
    }


    @PostMapping("saveProduct")
    public ModelAndView saveProduct(@Valid ProductPayload productPayload,
                                    BindingResult result,
                                    HttpServletRequest request,
                                    ModelMap modelMap) throws IOException {
        if (result.hasErrors()) {
            return new ModelAndView("Admin/product/NewProduct");
        }
        productService.save(productPayload, request);
        modelMap.addAttribute("message", "Product is Saved");
        return new ModelAndView("forward:/admin/product/newProduct", modelMap);

    }
}
