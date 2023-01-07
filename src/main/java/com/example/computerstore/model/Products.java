package com.example.computerstore.model;

import com.example.computerstore.Payload.ProductPayload;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId",nullable = false,unique = true)
    private int productId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productPrice")
    private int productPrice;

    @Column(name = "productDescription")
    private String productDescription;

    @Column(name = "productImageLink")
    private String productImageLink;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "brand")
    private String brand;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

   //    @Column(name = "active")
//    private boolean active = true;


//   @ManyToOne
//   @JoinColumn(name = "category_category_id")

    public Products() {

    }

    public Products(int productId, String productName, int productPrice, String productDescription, String productImageLink, int quantity, Category category,String brand) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productImageLink = productImageLink;
        this.quantity = quantity;
        this.category = category;
        this.brand = brand;
    }

    //    public boolean isActive() {
//        return active;
//    }
//
//    public void setActive(boolean active) {
//        this.active = active;
//    }








    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImageLink() {
        return productImageLink;
    }

    public void setProductImageLink(String productImageLink) {
        this.productImageLink = productImageLink;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
