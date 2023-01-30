package com.example.computerstore.service;

import com.example.computerstore.dao.CategoryRepository;
import com.example.computerstore.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAllByActiveTrue();
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category getCategoryByCategoryId(int categoryId) {
        return categoryRepository.getCategoriesByCategoryId(categoryId);
    }
}
