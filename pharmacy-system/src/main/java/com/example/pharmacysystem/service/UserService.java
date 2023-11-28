package com.example.pharmacysystem.service;


import com.example.pharmacysystem.model.User;

import java.util.List;

public interface UserService {
    public User getUser(String userName, String password);
    public int checkUser(String userName, String password);

}
