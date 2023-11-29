package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.exceptions.UserRegistrationException;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping(value = {"/user"})
@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user) {
        System.out.println("-----------------------------------------");
        System.out.println("In User Controller");
//        System.out.println("User: \n" + user.toString());
        try {
            userService.saveUser(user);
            // TODO: Add automatic login here
            return "New User is added successfully";
        } catch (UserRegistrationException e) {
            return e.getMessage(); // Return the specific error message
        } catch (Exception e) {
//            e.printStackTrace();
            return "Internal Server Error";
        }
    }
}
