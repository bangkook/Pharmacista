package com.example.pharmacysystem.utils;

import java.io.Serializable;
import java.util.Objects;

public class OrderDetailId implements Serializable {
    
    private int orderId;
    private String productSN;

    public OrderDetailId() {
    }

    public OrderDetailId(int orderId, String productSN) {
        this.orderId = orderId;
        this.productSN = productSN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailId that = (OrderDetailId) o;
        return orderId == that.orderId && productSN.equals(that.productSN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productSN);
    }
}
