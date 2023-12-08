package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.User;

import java.util.List;

public interface AdminService {
    List<User> viewUsers();

    List<User> viewAdmins();

    boolean promoteUser(int adminId, int userId);

    boolean addProduct(String SerialNumber);

    boolean updateProduct(String SerialNumber);

    boolean deleteProduct(String SerialNumber);

}
