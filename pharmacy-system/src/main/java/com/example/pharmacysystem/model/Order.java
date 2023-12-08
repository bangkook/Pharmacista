package com.example.pharmacysystem.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.sql.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;
    private String username;
    private Date dateCreated;
    private float totalPrice;

    public Order() {
    }

    public Order(String username, Date dateCreated, float totalPrice) {
        this.username = username;
        this.dateCreated = dateCreated;
        this.totalPrice = totalPrice;
    }

    @ReadOnlyProperty
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
