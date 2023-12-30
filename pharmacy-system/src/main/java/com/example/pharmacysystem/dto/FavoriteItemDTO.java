package com.example.pharmacysystem.dto;

public class FavoriteItemDTO {

    private int userId;
    private String productSN;
    private String productName;
    private float productPrice;
    private String productPhoto;

    public FavoriteItemDTO() {
    }

    public FavoriteItemDTO(int userId, String productSN, String productName, float productPrice, String productPhoto) {
        this.userId = userId;
        this.productSN = productSN;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productPhoto = productPhoto;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteItemDTO that = (FavoriteItemDTO) o;
        return userId == that.userId && Float.compare(that.productPrice, productPrice) == 0 && productSN.equals(that.productSN) && productName.equals(that.productName) && productPhoto.equals(that.productPhoto);
    }
    
}
