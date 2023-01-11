package com.example.computerstore.Payload;

import com.example.computerstore.model.Products;

import java.util.List;

public class OrderPayLoad {

    private  int orderId;

    private int quantityOrder;

    private Products product;

    public int getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(int quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}
