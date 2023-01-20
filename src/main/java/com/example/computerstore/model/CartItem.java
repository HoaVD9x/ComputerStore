package com.example.computerstore.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cartItem")
public class CartItem {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "cartItemId", nullable = false, unique = true)
    private int cartItemId;

    @Column(name = "quantity")
    private int quantityOrder;


    public CartItem() {
    }

    public CartItem(int cartItemId, int quantityOrder) {
        this.cartItemId = cartItemId;
        this.quantityOrder = quantityOrder;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getQuantityOrder() {
        return quantityOrder;
    }

    public void setQuantityOrder(int quantityOrder) {
        this.quantityOrder = quantityOrder;
    }

}
