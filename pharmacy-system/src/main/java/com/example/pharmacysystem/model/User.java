package com.example.pharmacysystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;//final
    private  String username;//final
    private String password;
    private boolean role;
    private String streetAddress;
    private String city;
    private String country;
    private String zipCode;
    private String phoneNumber;
    private String profilePicture;

    public User(int id, String username, String password, boolean role, String streetAddress, String city, String country, String zipCode, String phoneNumber, String profilePicture) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
    }

    public User(){}
    @ReadOnlyProperty
    public int getId() {
        return id;
    }

    @ReadOnlyProperty
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRole() {return role;}

    public void setRole(boolean role) {this.role = role;}



    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
