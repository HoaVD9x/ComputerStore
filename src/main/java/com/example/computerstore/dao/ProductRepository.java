package com.example.computerstore.dao;

import com.example.computerstore.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Integer> {
    List<Products> findByCategory_CategoryId(int categoryId);

    List<Products> findProductsByBrand(String Brand);

    List<Products> findProductsByProductPriceIsBetween(int min, int max);

    List<Products> findProductsByProductPriceAfter(int min);





}
