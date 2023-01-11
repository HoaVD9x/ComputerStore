package com.example.computerstore.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId", nullable = false, unique = true)
    private int cartId;

    @Column(name = "price")
    private int price;
   @ManyToOne
   @JoinColumn(name = "orderId")
    private Order order;


    public Cart(int cartId, int price, Order order) {
        this.cartId = cartId;
        this.price = price;
        this.order = order;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Cart() {

    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
