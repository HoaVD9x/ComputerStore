package com.example.computerstore.dao;

import com.example.computerstore.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Integer> {
    Page<Products> findByCategory_CategoryIdAndActiveTrue(int categoryId,Pageable pageable);

    Page<Products> findProductsByBrand(String Brand,Pageable pageable);

    Page<Products> findProductsByProductPriceIsBetweenAndActiveTrue(int min, int max, Pageable pageable);

    List<Products> findProductsByProductPriceAfter(int min);

    Products getProductsByProductId(int productId);

    List<Products> findAllByActiveTrue();

    Page<Products> getProductsByActiveTrue(Pageable pageable);

    List<Products> getProductsByBrand(String brand);





}
