package com.example.pharmacysystem.model;

public class UserInfo {

    private int userId;
    private String username;
    private String phoneNumber;
    private String profilePicture;

    public UserInfo() {
    }

    public UserInfo(int userId, String username, String phoneNumber, String profilePicture) {
        this.userId = userId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }
}
