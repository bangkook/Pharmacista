package com.example.pharmacysystem.service;


import com.example.pharmacysystem.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User saveUser(User user);

    public List<String> findAllUsers();

}
