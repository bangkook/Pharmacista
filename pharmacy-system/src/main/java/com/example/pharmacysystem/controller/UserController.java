package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.exceptions.UserException;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.service.UserService;
import com.example.pharmacysystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequestMapping(value = {"/user"})
@RestController
@CrossOrigin()
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>("New User is added successfully", HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); // Return the specific error message
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
                data.get(Constants.STREET),
                data.get(Constants.CITY),
                data.get(Constants.COUNTRY),
                data.get(Constants.ZIP_CODE),
                data.get(Constants.PHONE));
        if (updated) {
            return new ResponseEntity<>("User data updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update user data", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/upload-profile-picture/{id}")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable("id") int userId,
                                                       @RequestBody Map<String, String> profilePicture) {
        boolean uploaded = userService.uploadProfilePicture(userId, profilePicture.get(Constants.PICTURE));
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

    @GetMapping("/getUserByName")
    public User getUsersByName(@RequestParam String userName, @RequestParam String password) {
        return userService.getUser(userName, password);
    }

    @GetMapping("/checkUser")
    public ResponseEntity<String> checkUser(@RequestParam String userName, @RequestParam String password) {
        UserService.LoginStatus loginStatus = userService.checkUser(userName, password);
        return ResponseEntity.ok(loginStatus.name());
    }

    @GetMapping("/get-user-by-username/{username}")
    @ResponseBody
    public User getUserByUserName(@PathVariable("username") String userName) {
        return userService.getUserByUserName(userName);
    }
}
