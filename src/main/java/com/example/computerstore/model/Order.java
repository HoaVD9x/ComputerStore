package com.example.computerstore.model;

import com.example.computerstore.Payload.OrderPayLoad;
import jakarta.persistence.*;


@Entity
@Table(name = "'order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId", nullable = false, unique = true)
    private int orderId;

    @Column(name = "quantityOrder")
    private int quantityOrder;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    public Order(int orderId, int quantityOrder, Products product, Cart cart) {
        this.orderId = orderId;
        this.quantityOrder = quantityOrder;
        this.product = product;
        this.cart = cart;
    }

    public Order() {

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(int quantityOrder) {
        this.quantityOrder = quantityOrder;
    }


    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void orderToEntity(OrderPayLoad orderPayLoad) {
        this.orderId = orderPayLoad.getOrderId();
        this.quantityOrder = orderPayLoad.getQuantityOrder();
        this.product = orderPayLoad.getProduct();
    }
}
