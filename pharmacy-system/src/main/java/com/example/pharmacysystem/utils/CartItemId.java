package com.example.pharmacysystem.utils;

import java.io.Serializable;
import java.util.Objects;

public class CartItemId implements Serializable {

    private int userId;
    private String productSN;

    public CartItemId() {
    }

    public CartItemId(int userId, String productSN) {
        this.userId = userId;
        this.productSN = productSN;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemId that = (CartItemId) o;
        return userId == that.userId && productSN.equals(that.productSN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productSN);
    }
}
