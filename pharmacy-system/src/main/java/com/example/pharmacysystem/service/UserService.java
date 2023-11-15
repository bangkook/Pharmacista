package com.example.pharmacysystem.service;


import com.example.pharmacysystem.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {
    User getUserById(int id);

    boolean updateUserData(int id, String address, String city, String country, String zipCode, String phone);

    boolean uploadProfilePicture(int id, String profilePicture);

    PasswordEncoder encoder();

    boolean changePassword(int id, String currentPassword, String newPassword);

}
