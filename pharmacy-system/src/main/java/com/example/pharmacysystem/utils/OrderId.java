package com.example.pharmacysystem.utils;

import java.io.Serializable;
import java.util.Objects;

public class OrderId implements Serializable {
    private int id;
    private int userId;

    public OrderId() {
    }

    public OrderId(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId = (OrderId) o;
        return id == orderId.id && userId == orderId.userId;
    }

    public int hashCode() {
        return Objects.hash(id, userId);
    }
}
