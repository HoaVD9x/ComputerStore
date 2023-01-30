package com.example.computerstore.service;

import com.example.computerstore.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    void saveCategory(Category category);

    Category getCategoryByCategoryId(int categoryId);


}
