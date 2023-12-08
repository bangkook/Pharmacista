package com.example.pharmacysystem.model;

public class UserInfo {
    private String username;
    private String phoneNumber;
    private String profilePicture;

    public UserInfo() {
    }

    public UserInfo(String username, String phoneNumber, String profilePicture) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
