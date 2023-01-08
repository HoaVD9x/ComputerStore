package com.example.computerstore.service;

import com.example.computerstore.Payload.ProductPayload;
import com.example.computerstore.model.Products;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface ProductService {
    void save(ProductPayload productPayload, HttpServletRequest request) throws IOException;


    List<Products> getProductByCategoryId(int categoryId);

    List<Products> getProductbyBrand(String brand);

    List<Products> findAll();

    Products getProductById(int productId);





}
