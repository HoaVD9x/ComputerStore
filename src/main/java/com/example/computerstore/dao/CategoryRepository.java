package com.example.computerstore.dao;

import com.example.computerstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CategoryRepository extends JpaRepository<Category,Integer> {
     Optional<Category> findByCategoryName(String categoryName);
}
