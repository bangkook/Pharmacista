package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    public User saveUser(User user) {
        return null;
    }

    @Override
    public List<String> findAllUsers() {

        return null;
    }

}
