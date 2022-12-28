package com.example.computerstore.service;

import com.example.computerstore.Payload.ProductPayload;
import com.example.computerstore.model.Products;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void save(ProductPayload productPayload, HttpServletRequest request) throws IOException;

    List<Products> getAll();
}
