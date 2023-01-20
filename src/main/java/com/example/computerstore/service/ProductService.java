package com.example.computerstore.service;

import com.example.computerstore.Payload.ProductPayload;
import com.example.computerstore.model.Product;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    void save(ProductPayload productPayload, HttpServletRequest request)throws IOException;


    Page<Product> getProductByCategoryId(int categoryId, Pageable pageable);

    Page<Product> getProductbyBrand(String brand, Pageable pageable);

    List<Product> findAll();

    Optional<Product> getProductById(int productId);

    Page<Product>getProductByMinMaxPrice(int min, int max, Pageable pageable);

    List<Product>getProductPriceMin(int min);

    Product saveProduct(Product product);

    List<Product> findAllProductByBrand(String brand);



}
