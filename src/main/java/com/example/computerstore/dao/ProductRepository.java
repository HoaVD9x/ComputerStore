package com.example.computerstore.dao;

import com.example.computerstore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByCategory_CategoryIdAndActiveTrue(int categoryId, Pageable pageable);


    Page<Product> findProductsByBrandAndActiveTrue(String Brand, Pageable pageable);

    Page<Product> findProductsByProductPriceIsBetweenAndActiveTrue(int min, int max, Pageable pageable);

    List<Product> findProductsByProductPriceAfter(int min);

    List<Product> findAllByActiveTrue();

    Page<Product> getProductsByActiveTrue(Pageable pageable);

    List<Product> getProductsByBrand(String brand);


    Optional<Product> getProductsByProductId(int productId);
}
