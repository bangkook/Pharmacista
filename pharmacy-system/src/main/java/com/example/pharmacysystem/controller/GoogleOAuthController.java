package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.service.UserService;
import com.example.pharmacysystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GoogleOAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login/oauth2/")
    public ResponseEntity<String> createUser(@RequestBody Map<String, String> Response) {//Registration Rest API
        // Retrieve user information from the Response
        String email = Response.get(Constants.EMAIL);
        String picture = Response.get(Constants.PICTURE);
        System.out.println(Response);

        List<String> existingUsers = userService.findAllUsers();
        if (existingUsers.contains(email)) {
            return ResponseEntity.ok("Existing User " + email);
        } else {
            User newUser = new User(email, null, null, null, null, null, null, picture);
            userService.saveUserGoogle(newUser);
            return ResponseEntity.ok("New User added " + email);
        }
    }

}
