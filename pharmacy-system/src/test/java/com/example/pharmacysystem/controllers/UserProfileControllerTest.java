package com.example.pharmacysystem.controllers;

import com.example.pharmacysystem.controller.UserController;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserProfileControllerTest {
    
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    public void testUpdateUserData_Successfully() {
        // Mock data
        int userId = 1;
        String streetAddress = "New Address";
        String city = "New City";
        String country = "New Country";
        String zip = "11111";
        String phoneNumber = "1234567890";

        // Mock the service
        when(userService.updateUserData(userId, streetAddress, city, country, zip, phoneNumber))
                .thenReturn(true);

        Map<String, String> data = new HashMap<>();
        data.put("streetAddress", streetAddress);
        data.put("city", city);
        data.put("country", country);
        data.put("zipCode", zip);
        data.put("phoneNumber", phoneNumber);

        // Test controller methods
        ResponseEntity<String> responseEntity = userController.updateUserData(userId, data);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User data updated successfully", responseEntity.getBody());

    }

    @Test
    public void testUpdateUserData_UserNotFound() {
        // Mock data
        int userId = 1;
        String streetAddress = "New Address";
        String city = "New City";
        String country = "New Country";
        String zip = "11111";
        String phoneNumber = "1234567890";

        // Mock the service
        when(userService.updateUserData(userId, streetAddress, city, country, zip, phoneNumber))
                .thenReturn(false);

        Map<String, String> data = new HashMap<>();
        data.put("streetAddress", streetAddress);
        data.put("city", city);
        data.put("country", country);
        data.put("zipCode", zip);
        data.put("phoneNumber", phoneNumber);

        // Test controller methods
        ResponseEntity<String> responseEntity = userController.updateUserData(userId, data);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Failed to update user data", responseEntity.getBody());

    }

    @Test
    public void testGetUserData_Successfully() {
        // Mock data
        int userId = 1;
        User mockUser = new User("username", "password", "address", "city",
                "country", "11111", "12345678911", "picture.jpg");

        // Mock the service
        when(userService.getUserById(userId)).thenReturn(mockUser);

        // Test controller methods
        ResponseEntity<User> responseEntity = userController.getUser(userId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUser, responseEntity.getBody());

    }

    @Test
    public void testGetUserData_UserNotFound() {
        // Mock data
        int nonExistentUserId = 2;

        // Mock the service
        when(userService.getUserById(nonExistentUserId)).thenThrow(RuntimeException.class);

        // Test controller methods
        ResponseEntity<User> responseEntity = userController.getUser(nonExistentUserId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    public void testUploadProfilePicture_Successful() {
        // Mock data
        int userId = 1;

        String profilePicture = "picture.jpg";

        // Mock the service
        when(userService.uploadProfilePicture(userId, profilePicture))
                .thenReturn(true);

        // Test controller methods
        ResponseEntity<String> responseEntity = userController.uploadProfilePicture(userId, profilePicture);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Profile picture uploaded successfully", responseEntity.getBody());

    }

    @Test
    public void testUploadProfilePicture_UserNotFound() {
        // Mock data
        int nonExistentUserId = 1;

        String profilePicture = "picture.jpg";

        // Mock the service
        when(userService.uploadProfilePicture(nonExistentUserId, profilePicture))
                .thenReturn(false);

        // Test controller methods
        ResponseEntity<String> responseEntity = userController.uploadProfilePicture(nonExistentUserId, profilePicture);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Failed to upload. User not found", responseEntity.getBody());
    }

    @Test
    public void testChangePassword_Successfully() {
        // Mock data
        int userId = 1;

        String currentPassword = "old password";
        String newPassword = "new password";

        // Mock the service
        when(userService.changePassword(userId, currentPassword, newPassword))
                .thenReturn(true);

        // Test controller methods
        ResponseEntity<String> responseEntity = userController.changePassword(userId, currentPassword, newPassword);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Password changed successfully", responseEntity.getBody());

    }

    @Test
    public void testChangePassword_WrongCurrentPassword() {
        // Mock data
        int userId = 1;

        String wrongCurrentPassword = "wrong password";
        String newPassword = "new password";

        // Mock the service
        when(userService.changePassword(userId, wrongCurrentPassword, newPassword))
                .thenReturn(false);

        // Test controller methods
        ResponseEntity<String> responseEntity = userController.changePassword(userId, wrongCurrentPassword, newPassword);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals("Current password is wrong", responseEntity.getBody());

    }
}
