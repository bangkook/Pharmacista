package com.example.pharmacysystem.utils;

import java.io.Serializable;
import java.util.Objects;

public class FavoriteItemId implements Serializable {

    private int userId;
    private String productSN;

    public FavoriteItemId() {
    }

    public FavoriteItemId(int userId, String productSN) {
        this.userId = userId;
        this.productSN = productSN;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProductSN() {
        return productSN;
    }

    public void setProductSN(String productSN) {
        this.productSN = productSN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteItemId that = (FavoriteItemId) o;
        return userId == that.userId && productSN.equals(that.productSN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productSN);
    }
}
