package com.example.computerstore.Payload;

import com.example.computerstore.model.Product;

public class OrderPayLoad {

    private  int orderId;

    private int quantityOrder;

    private Product product;

    private int totalPrice;

    public OrderPayLoad(int orderId, int quantityOrder, Product product, int totalPrice) {
        this.orderId = orderId;
        this.quantityOrder = quantityOrder;
        this.product = product;
        this.totalPrice = totalPrice;
    }

    public OrderPayLoad() {
    }

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
