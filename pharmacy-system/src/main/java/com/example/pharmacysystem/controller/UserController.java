package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin()
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUserByName")
    public User getUsersByName(@RequestParam String userName, @RequestParam String password){
        return null;
    }
    @GetMapping("/checkUser")
    public int checkUser(@RequestParam String userName, @RequestParam String password){
        return 0;
    }
}
