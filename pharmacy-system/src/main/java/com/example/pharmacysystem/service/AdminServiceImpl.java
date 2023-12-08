package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.User;

import java.util.List;

public class AdminServiceImpl extends UserServiceImpl implements AdminService {
    @Override
    public List<User> viewUsers() {
        return null;
    }

    @Override
    public List<User> viewAdmins() {
        return null;
    }

    @Override
    public boolean promoteUser(int adminId ,int userId) {
        return false;
    }

    @Override
    public boolean addProduct(String SerialNumber) {
        return false;
    }

    @Override
    public boolean updateProduct(String SerialNumber) {
        return false;
    }

    @Override
    public boolean deleteProduct(String SerialNumber) {
        return false;
    }
}
