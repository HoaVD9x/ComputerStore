package com.example.computerstore.controller;

import com.example.computerstore.model.Category;
import com.example.computerstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("")
    public String listCategory(Model model) {
        List<Category> category = categoryService.getAll();
        model.addAttribute("categorys", category);
        return "Admin/Category/ListCategory";
    }

    @GetMapping("newCategory")
    public String newCategory(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("message", "add new Category !" );
        return "Admin/Category/NewCategory";
    }

    @GetMapping("detail")
    public String detailCategory(Model model,
                                 @RequestParam("categoryId") int categoryId) {
        Category category = categoryService.getCategoryByCategoryId(categoryId);
        model.addAttribute("category", category);
        return "Admin/Category/NewCategory";
    }

    @GetMapping("delete")
    public String deleteCategory(Model model,
                                 @RequestParam("categoryId") int categoryId) {
        Category category = categoryService.getCategoryByCategoryId(categoryId);
        category.setActive(false);
        categoryService.saveCategory(category);
        model.addAttribute("message", "category is upDate");
        model.addAttribute("categorys", categoryService.getAll());
        return "Admin/Category/ListCategory";
    }

    @PostMapping("newCategory")
    public String saveCategory(Model model,
                               @ModelAttribute Category category) {
        categoryService.saveCategory(category);
        model.addAttribute("message", "category is update");
        model.addAttribute("categorys", categoryService.getAll());
        return "Admin/Category/ListCategory";
    }

}
