package com.example.pharmacysystem.model;

import com.example.pharmacysystem.utils.CartItemId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import org.springframework.data.annotation.ReadOnlyProperty;

@Entity
@IdClass(CartItemId.class)
public class CartItem {

    @Id
    private int userId;
    @Id
    private String productSN;

    private int quantity;

    public CartItem() {
    }

    public CartItem(int userId, String productSN, int quantity) {
        this.userId = userId;
        this.productSN = productSN;
        this.quantity = quantity;
    }

    @ReadOnlyProperty
    public int getUserId() {
        return userId;
    }

    @ReadOnlyProperty
    public String getProductSN() {
        return productSN;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "userId=" + userId +
                ", productSN='" + productSN + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}