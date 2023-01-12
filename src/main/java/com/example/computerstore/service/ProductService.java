package com.example.computerstore.service;

import com.example.computerstore.Payload.ProductPayload;
import com.example.computerstore.model.Products;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void save(ProductPayload productPayload, HttpServletRequest request) throws IOException;


    Page<Products> getProductByCategoryId(int categoryId, Pageable pageable);

    Page<Products> getProductbyBrand(String brand, Pageable pageable);

    List<Products> findAll();

    Products getProductById(int productId);

    Page<Products>getProductByMinMaxPrice(int min, int max, Pageable pageable);

    List<Products>getProductPriceMin(int min);

    Products saveProduct(Products product);

    List<Products> findAllProductByBrand(String brand);



}
