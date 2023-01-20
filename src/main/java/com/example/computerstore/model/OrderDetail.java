package com.example.computerstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orderDetail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int OrderDetailId;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
@Column(nullable = false)
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "oderId")
    private  Order order;

    public OrderDetail(int orderDetailId, int quantity, Product product, Order order) {
       this.OrderDetailId = orderDetailId;
        this.quantity = quantity;
        this.product = product;
        this.order = order;
    }

    public OrderDetail() {

    }

    public int getOrderDetailId() {
        return OrderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        OrderDetailId = orderDetailId;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
