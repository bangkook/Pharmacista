package com.example.pharmacysystem.service;


import com.example.pharmacysystem.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User saveUser(User user);
    public User findByUsername(String username);
    public boolean isUsernameFound(String username);
}
