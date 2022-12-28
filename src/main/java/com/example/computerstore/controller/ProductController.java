package com.example.computerstore.controller;

import com.example.computerstore.Payload.ProductPayload;
import com.example.computerstore.dao.CategoryRepository;
import com.example.computerstore.model.Category;
import com.example.computerstore.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("newProduct")
    public ModelAndView newProduct(ModelMap modelMap) {
        modelMap.addAttribute("product", new ProductPayload());
        List<Category> categoryList = categoryRepository.findAll();
        categoryList.stream().distinct().collect(Collectors.toSet());
        modelMap.addAttribute("categorys", categoryList);
        return new ModelAndView("product/newProduct", modelMap);
    }

    @GetMapping("/newCategory")
    public String newCategory (Model model) {
        model.addAttribute("category",new Category());
        return "product/newCategory";
    }

    @PostMapping("/saveProduct")
    public ModelAndView newProduct(@ModelAttribute ProductPayload productPayload, HttpServletRequest request, ModelMap modelMap) throws IOException {
        service.save(productPayload, request);
        String message = "success !";
        modelMap.put("massage", message);
        return new ModelAndView("product/newProduct", "message", message);

    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, Model model) {
       Optional<Category> categoryOptional = categoryRepository.findByCategoryName(category.getCategoryName());
       if (categoryOptional.isPresent()) {
           model.addAttribute("message","sorry category Name is exists");
           return "product/newCategory";
       } else {
           categoryRepository.save(category);
           model.addAttribute("message", "sucessfully");
           return "product/newCategory";
       }
    }
}
