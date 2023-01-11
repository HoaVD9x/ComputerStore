package com.example.computerstore.controller;

import com.example.computerstore.Payload.OrderPayLoad;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


import java.util.ArrayList;
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

        // pageable
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

        // category
        model.addAttribute("categorys", categoryRepository.findAll());

        // brand
        List<Products> productsList = productService.findAll();
        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);

        // order
        model.addAttribute("orderPayload", new OrderPayLoad());

        return "product/products";
    }

    @GetMapping("detail/{productId}")
    public ModelAndView detailProduct(@PathVariable("productId") int productId, ProductPayload productPayload, Model model,
                                      @RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size) {

        // pageable
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
    public String getProductByCategoryId(@PathVariable("categoryId") int categoryId,
                                         Model model,
                                         @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("size") Optional<Integer> size) {

        // pageable
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
        List<Products> productsList = productService.findAll();
        model.addAttribute("categorys", categoryRepository.findAll());
        model.addAttribute("products", productService.getProductByCategoryId(categoryId));
        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);
        return "product/products";
    }

    @GetMapping("brand/{brand}")
    public String getProductByBrand(@PathVariable("brand") String brand,
                                    Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {

        // pageable
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
        List<Products> productsList = productService.findAll();
        model.addAttribute("categorys", categoryRepository.findAll());
        model.addAttribute("products", productService.getProductbyBrand(brand));
        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);
        return "product/products";
    }

    @GetMapping("price")
    public String getProductByPrice(Model model,
                                    @RequestParam("min") int min,
                                    @RequestParam("max") int max,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {

        // pageable
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
        List<Products> getMinMaxPrice = productService.getProductByMinMaxPrice(min, max);
        model.addAttribute("products",getMinMaxPrice);
        // category
        model.addAttribute("categorys", categoryRepository.findAll());

        // brand
        List<Products> productsList = productService.findAll();
        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);
        return "product/products";
    }

    @GetMapping("MinPrice")
    public String getProductByPriceMin(Model model,
                                       @RequestParam("min") int min,
                                       @RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size) {

        // pageable
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
        List<Products> getMinPrice = productService.getProductPriceMin(min);
        model.addAttribute("products",getMinPrice);
        // category
        model.addAttribute("categorys", categoryRepository.findAll());

        // brand
        List<Products> productsList = productService.findAll();
        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);
        return "product/products";
    }



    @PostMapping("saveProduct")
    public String saveProduct(@ModelAttribute ProductPayload productPayload,
                                    @Validated BindingResult result,
                                    HttpServletRequest request,
                                    Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) throws IOException {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError objectError : result.getAllErrors()) {
                if (!errorList.contains(objectError.getDefaultMessage())){
                    errorList.add(objectError.getDefaultMessage());
                }
            }
            model.addAttribute("validationError",errorList);
            model.addAttribute("product",new ProductPayload());
            return "product/newProduct";
        }

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Products> productsPage = productRepository.findAll(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        List<Products> productsList = productService.findAll();
        model.addAttribute("products", productsList);
        model.addAttribute("categorys", categoryRepository.findAll());


        productService.save(productPayload, request);

        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);
        String message = "success save product" + productPayload.getProductName() + " !";
        model.addAttribute("message", message);
        return "product/products";


    }
}
