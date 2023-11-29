package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User getUser(String userName, String password) {
        List<User> Users= userRepository.findAll();
        if(userName==null || password==null) return null;
        User user=null;
        for(User u:Users){
            if(u.getUsername().equals(userName) && u.getPassword().equals(password)) {
                user = u;
                break;
            }
        }
        return user;
    }
    public int checkUser(String userName, String password){
        List<User> Users= userRepository.findAll();
        if(userName==null || password==null) return -1;
        for(User u:Users){
            if(u.getUsername().equals(userName) ) {
                if (u.getPassword().equals(password)) {
                    return 1;//user found and correct password
                }
                return 0; //user found but incorrect password
            }
        }
        return -1;//user not in database
    }
}
