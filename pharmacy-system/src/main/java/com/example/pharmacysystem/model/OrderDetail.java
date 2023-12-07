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
    private int productId;
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(int orderId, int productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
