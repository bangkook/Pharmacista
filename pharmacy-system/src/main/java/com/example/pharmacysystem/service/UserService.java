package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.User;

import java.util.List;

public interface UserService {
    User getUserById(int id);

    boolean updateUserData(int id, String address, String city, String country, String zipCode, String phone);

    boolean uploadProfilePicture(int id, String profilePicture);

    boolean changePassword(int id, String currentPassword, String newPassword);

    List<String> getUsernames();

    Boolean currentUserEmail(String email);

    User saveUser(User user);

    List<String> findAllUsers();
}
