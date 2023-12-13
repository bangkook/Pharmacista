package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User findByUsername(String username);

    boolean isUsernameFound(String username);

    User getUserById(int id);

    boolean updateUserData(int id, String address, String city, String country, String zipCode, String phone);

    boolean uploadProfilePicture(int id, String profilePicture);

    boolean changePassword(int id, String currentPassword, String newPassword);

    List<String> getUsernames();

    Boolean currentUserEmail(String email);

    User saveUser(User user);

    User saveUserGoogle(User user);

    List<String> findAllUsers();

    User getUser(String userName, String password);

    User getUserByUserName(String userName);

    LoginStatus checkUser(String userName, String password);

    enum LoginStatus {
        USER_FOUND_CORRECT_PASSWORD,
        USER_FOUND_INCORRECT_PASSWORD,
        USER_NOT_FOUND,
        INVALID_INPUT
    }
}
