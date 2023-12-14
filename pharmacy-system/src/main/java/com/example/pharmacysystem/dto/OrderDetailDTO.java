package com.example.pharmacysystem.dto;

import com.example.pharmacysystem.model.Product;

public class OrderDetailDTO {

    private int orderId;
    private Product product;
    private int quantity;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(int orderId, Product product, int quantity) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
