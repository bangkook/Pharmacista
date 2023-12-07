package com.example.pharmacysystem.utils;

import java.io.Serializable;
import java.util.Objects;

public class OrderDetailId implements Serializable {
    private int orderId;
    private int productId;

    public OrderDetailId() {
    }

    public OrderDetailId(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailId that = (OrderDetailId) o;
        return orderId == that.orderId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
