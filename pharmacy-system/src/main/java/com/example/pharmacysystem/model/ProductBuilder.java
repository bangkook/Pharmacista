package com.example.pharmacysystem.model;

import java.util.Date;

public class ProductBuilder {

    private final Product product;

    public ProductBuilder(Product product) {
        this.product = product;
    }

    public ProductBuilder buildPrice(double price) {
        this.product.setPrice(price);
        return this;
    }

    public ProductBuilder buildProductionDate(Date productionDate) {
        this.product.setProductionDate(productionDate);
        return this;
    }

    public ProductBuilder buildExpiryDate(Date expiryDate) {
        this.product.setExpiryDate(expiryDate);
        return this;
    }

    public ProductBuilder buildDescription(String description) {
        this.product.setDescription(description);
        return this;
    }

    public ProductBuilder buildQuantity(int quantity) {
        this.product.setQuantity(quantity);
        return this;
    }

    public ProductBuilder buildName(String name) {
        this.product.setName(name);
        return this;
    }

    public ProductBuilder buildPhoto(String photo) {
        this.product.setPhoto(photo);
        return this;
    }

    public Product build() {
        return this.product;
    }
}
