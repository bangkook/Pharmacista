package com.example.pharmacysystem.controller;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.service.UserService;
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

    private final UserService userService;
    // Inject the UserService through constructor injection
    public GoogleOAuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login/oauth2/")
    public ResponseEntity<String> createUser(@RequestBody Map<String, Object> payload) {//Registration Rest API
//         Retrieve user information from the payload
        String email = (String) payload.get("email");
        String picture = (String) payload.get("picture");
        System.out.println(payload);


        List<String> existingUsers = userService.findAllUsers();
        if (existingUsers.contains(email)) {
            return ResponseEntity.ok("Existing User " + email);
        } else {
            User newUser = new User(0, email, null, null, null, null, null, null, picture);
            userService.saveUser(newUser);
            return ResponseEntity.ok("New User added " + email);
        }
    }

}