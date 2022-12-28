package com.example.computerstore.Payload;

import com.example.computerstore.model.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.springframework.web.multipart.MultipartFile;

public class ProductPayload {

    private String productName;

    private String productPrice;

    private int quantity;
    private String productDescription;

    private Category category;

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public ProductPayload() {
    }

    public ProductPayload(int productId, String productName, String productPrice, String productDescription, int quantity, Category category, MultipartFile file) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.quantity = quantity;
        this.category = category;
        this.file = file;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
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
}
