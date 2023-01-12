package com.example.computerstore.controller;

import com.example.computerstore.Payload.OrderPayLoad;
import com.example.computerstore.Payload.ProductPayload;
import com.example.computerstore.dao.CategoryRepository;
import com.example.computerstore.dao.ProductRepository;
import com.example.computerstore.model.Order;
import com.example.computerstore.model.Products;
import com.example.computerstore.service.CartService;
import com.example.computerstore.service.OrderService;
import com.example.computerstore.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator;
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

    @Autowired
    private OrderService  orderService;


    @GetMapping("newProduct")
    public String newProduct(Model model) {

        model.addAttribute("product", new ProductPayload());
        model.addAttribute("categorys", categoryRepository.findAll());


        List<Order> orderList = orderService.findAll();
        List<Integer> ints = orderList.stream().map(Order::getQuantityOrder).collect(Collectors.toList());
        int sumQuantityOrder = ints.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sum",sumQuantityOrder);

        model.addAttribute("message", "Add New Product !");

        return "product/newProduct";
    }
    @GetMapping("products")
    public String AllProduct(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        // pageable
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Products> productsPage = productRepository.getProductsByActiveTrue(PageRequest.of(currentPage - 1, pageSize));
        productsPage.stream().collect(Collectors.toList());


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

        //pageable
        model.addAttribute("pathvariable","products");

        // order
        List<Order> orderList = orderService.findAll();
        List<Integer> ints = orderList.stream().map(Order::getQuantityOrder).collect(Collectors.toList());
        int sumQuantityOrder = ints.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sum",sumQuantityOrder);

        return "product/products";
    }

    @GetMapping("detail/{productId}")
    public String detailProduct(@PathVariable("productId") int productId,
                                      Model model) {
        Products product = productService.getProductById(productId);
        ProductPayload productPayload = new ProductPayload();
        productPayload.setProductId(product.getProductId());
        model.addAttribute("product",productPayload);


        List<Order> orderList = orderService.findAll();
        List<Integer> ints = orderList.stream().map(Order::getQuantityOrder).collect(Collectors.toList());
        int sumQuantityOrder = ints.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sum",sumQuantityOrder);

        model.addAttribute("message","edit Product " + product.getProductName());

        return "product/newProduct";
    }
    @GetMapping("categoryId/{categoryId}")
    public String getProductByCategoryId(@PathVariable("categoryId") int categoryId,
                                         Model model,
                                         @RequestParam("page") Optional<Integer> page,
                                         @RequestParam("size") Optional<Integer> size) {

        // pageable
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<Products> productsPage = productService.getProductByCategoryId(categoryId, PageRequest.of(currentPage - 1, pageSize) );
        productsPage.stream().collect(Collectors.toList());
        model.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", productsPage);


        List<Products> productsList = productService.findAll();
        model.addAttribute("categorys", categoryRepository.findAll());


        model.addAttribute("pathvariable","categoryId");


        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);

        List<Order> orderList = orderService.findAll();
        List<Integer> ints = orderList.stream().map(Order::getQuantityOrder).collect(Collectors.toList());
        int sumQuantityOrder = ints.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sum",sumQuantityOrder);

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
        Page<Products> productsPage = productService.getProductbyBrand(brand, PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("productPage", productsPage);
        int totalPages = productsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("products", productsPage);


        List<Products> productsList = productService.findAll();
        model.addAttribute("categorys", categoryRepository.findAll());


        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);


        model.addAttribute("pathvariable","brand");


        List<Order> orderList = orderService.findAll();
        List<Integer> ints = orderList.stream().map(Order::getQuantityOrder).collect(Collectors.toList());
        int sumQuantityOrder = ints.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sum",sumQuantityOrder);


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
        Page<Products> productsPage = productService.getProductByMinMaxPrice(min, max,PageRequest.of(currentPage - 1, pageSize));
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

        model.addAttribute("pathvariable","price");

        List<Order> orderList = orderService.findAll();
        List<Integer> ints = orderList.stream().map(Order::getQuantityOrder).collect(Collectors.toList());
        int sumQuantityOrder = ints.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sum",sumQuantityOrder);
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
        model.addAttribute("products", getMinPrice);
        // category
        model.addAttribute("categorys", categoryRepository.findAll());

        // brand
        List<Products> productsList = productService.findAll();
        List<String> brandList = productsList.stream().map(Products::getBrand).distinct().collect(Collectors.toList());
        model.addAttribute("brands", brandList);


        List<Order> orderList = orderService.findAll();
        List<Integer> ints = orderList.stream().map(Order::getQuantityOrder).collect(Collectors.toList());
        int sumQuantityOrder = ints.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sum",sumQuantityOrder);

        return "product/products";
    }

    @GetMapping("delete/{productId}")
    public String deleteProduct (@PathVariable("productId") int productId, Model model){

        Products product = productService.getProductById(productId);
        product.setActive(false);
        model.addAttribute("message","successful delete " + product.getProductName());
        productService.saveProduct(product);
        List<Products> productsList = productService.findAll();
        model.addAttribute("products", productsList);
        model.addAttribute("categorys", categoryRepository.findAll());


        List<Order> orderList = orderService.findAll();
        List<Integer> ints = orderList.stream().map(Order::getQuantityOrder).collect(Collectors.toList());
        int sumQuantityOrder = ints.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sum",sumQuantityOrder);

        return "product/products";

    }


    @PostMapping("saveProduct")
    public String saveProduct(@ModelAttribute ProductPayload productPayload,
                              HttpServletRequest request,
                              Model model,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) throws IOException {

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


        List<Order> orderList = orderService.findAll();
        List<Integer> ints = orderList.stream().map(Order::getQuantityOrder).collect(Collectors.toList());
        int sumQuantityOrder = ints.stream().reduce(0, (a, b) -> a + b);
        model.addAttribute("sum",sumQuantityOrder);

        return "product/products";


    }
}
