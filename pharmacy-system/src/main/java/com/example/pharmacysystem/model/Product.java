package com.example.pharmacysystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.sql.Date;

@Entity
public class Product {

    @Id
    private String serialNumber;
    private float price;
    private Date productionDate;
    private Date expiryDate;
    private String description;
    private int quantity;
    private String name;
    @Lob
    @Column(length = 1000000) // Adjust the size as needed
    private String photo;

    public Product(String serialNumber, float price, Date productionDate, Date expiryDate, String description, int quantity, String name, String photo) {
        this.serialNumber = serialNumber;
        this.price = price;
        this.productionDate = productionDate;
        this.expiryDate = expiryDate;
        this.description = description;
        this.quantity = quantity;
        this.name = name;
        this.photo = photo;
    }

    public Product() {
    }


    @ReadOnlyProperty
    public String getSerialNumber() {
        return serialNumber;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String toString() {
        return "Serial Number: " + serialNumber + "\n" +
                "Price: " + price + "\n" +
                "Production Date: " + productionDate + "\n" +
                "Expiry Date: " + expiryDate + "\n" +
                "Description: " + description + "\n" +
                "Quantity: " + quantity + "\n" +
                "Name: " + name + "\n" +
                "Photo: " + photo + "\n";
    }
}
