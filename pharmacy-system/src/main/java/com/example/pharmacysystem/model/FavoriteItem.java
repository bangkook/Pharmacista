package com.example.pharmacysystem.model;

import com.example.pharmacysystem.utils.FavoriteItemId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorite_items")
@IdClass(FavoriteItemId.class)
public class FavoriteItem {

    @Id
    private int userId;
    @Id
    private String productSN;

    public FavoriteItem() {
    }

    public FavoriteItem(int userId, String productSN) {
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
        FavoriteItem that = (FavoriteItem) o;
        return userId == that.userId && productSN.equals(that.productSN);
    }
}
