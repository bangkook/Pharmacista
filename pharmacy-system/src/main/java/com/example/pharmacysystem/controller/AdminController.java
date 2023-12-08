package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.exceptions.UserException;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.model.UserInfo;
import com.example.pharmacysystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin()
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("/promote-to-admin/{adminId}/{userId}")
    public ResponseEntity<String> promoteUserToAdmin(@PathVariable int adminId, @PathVariable int userId) {
        boolean success = adminService.promoteUser(adminId, userId);

        if (success) {
            return ResponseEntity.ok("User promoted to admin successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or promotion failed");
        }
    }

    @GetMapping("/{adminId}/findUsers")
    public List<UserInfo> getAllUsers(@PathVariable int adminId) {
        return adminService.viewUsers(adminId);
    }

    @GetMapping("/{adminId}/findAdmins")
    public List<UserInfo> getAllAdmins(@PathVariable int adminId) {
        return adminService.viewAdmins(adminId);
    }

    @GetMapping("/searchUser/{adminId}/{username}")
    public ResponseEntity<UserInfo> searchByUsername(@PathVariable int adminId, @PathVariable String username) {
        try {
            UserInfo userInfo = adminService.searchByUsername(adminId, username);
            return new ResponseEntity<>(userInfo, HttpStatus.OK);
        }  catch (UserException e) {
            return new ResponseEntity<> (null, HttpStatus.OK);
        }
    }

}
