package com.example.pharmacysystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.Date;

@Entity
public class Product {

    @Id
    private String serialNumber;
    private double price;
    private Date productionDate;
    private Date expiryDate;
    private String description;
    private int quantity;
    private String name;
    private String photo;

    public Product(String serialNumber, double price, Date productionDate, Date expiryDate, String description, int quantity, String name, String photo) {
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

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public String convertToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Serial Number: ").append(serialNumber).append("\n");
        stringBuilder.append("Price: ").append(price).append("\n");
        stringBuilder.append("Production Date: ").append(productionDate).append("\n");
        stringBuilder.append("Expiry Date: ").append(expiryDate).append("\n");
        stringBuilder.append("Description: ").append(description).append("\n");
        stringBuilder.append("Quantity: ").append(quantity).append("\n");
        stringBuilder.append("Name: ").append(name).append("\n");
        stringBuilder.append("Photo: ").append(photo).append("\n");

        return stringBuilder.toString();
    }
}
