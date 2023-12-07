package com.example.pharmacysystem.model;

public class UserBuilder {

    private final User user;

    public UserBuilder(User user) {
        this.user = user;
    }

    public UserBuilder buildAddress(String streetAddress, String city, String country, String zipCode) {
        this.user.setStreetAddress(streetAddress);
        this.user.setCity(city);
        this.user.setCountry(country);
        this.user.setZipCode(zipCode);
        return this;
    }

    public UserBuilder buildPhoneNumber(String phoneNumber) {
        this.user.setPhoneNumber(phoneNumber);
        return this;
    }

    public UserBuilder buildPassword(String password) {
        this.user.setPassword(password);
        return this;
    }

    public UserBuilder buildProfilePicture(String profilePicture) {
        this.user.setProfilePicture(profilePicture);
        return this;
    }

    public User build() {
        return this.user;
    }

}
