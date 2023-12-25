package com.example.pharmacysystem.model;

import com.example.pharmacysystem.utils.OrderDetailId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(OrderDetailId.class)
public class OrderDetail {

    @Id
    private int orderId;
    @Id
    private String productSN; // Serial Number
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(int orderId, String productSN, int quantity) {
        this.orderId = orderId;
        this.productSN = productSN;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getProductSN() {
        return productSN;
    }

    public int getQuantity() {
        return quantity;
    }
}
