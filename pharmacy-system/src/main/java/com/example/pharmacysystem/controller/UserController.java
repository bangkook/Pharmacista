package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin()
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("get-user/{id}")
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable("id") int userId) {
        try {
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update-data/{id}")
    public ResponseEntity<String> updateUserData(@PathVariable("id") int userId,
                                                 @RequestBody Map<String, String> data) {
        boolean updated = userService.updateUserData(userId,
                data.get("streetAddress"),
                data.get("city"),
                data.get("country"),
                data.get("zipCode"),
                data.get("phoneNumber"));
        if (updated) {
            return new ResponseEntity<>("User data updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update user data", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/upload-profile-picture/{id}")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable("id") int userId,
                                                       @RequestParam String profilePicture) {
        boolean uploaded = userService.uploadProfilePicture(userId, profilePicture);
        if (uploaded) {
            return new ResponseEntity<>("Profile picture uploaded successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to upload. User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/change-password/{id}")
    public ResponseEntity<String> changePassword(@PathVariable("id") int userId,
                                                 @RequestParam String currentPassword,
                                                 @RequestParam String newPassword) {
        boolean changedPassword = userService.changePassword(userId, currentPassword, newPassword);
        if (changedPassword) {
            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Current password is wrong", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
