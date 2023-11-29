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

    @PostMapping("/login/oauth2/")
    public ResponseEntity<String> createUser(@RequestBody Map<String, Object> payload) {//Registration Rest API
        return null;
    }

}

