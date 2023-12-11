package com.example.pharmacysystem.service;


import com.example.pharmacysystem.exceptions.AdminException;
import com.example.pharmacysystem.exceptions.UserException;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.model.UserInfo;
import com.example.pharmacysystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl extends UserServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserInfo> viewUsers(int adminId) {
        if (!isAdmin(adminId)) throw new AdminException("User is not authorized to perform this action");
        List<User> users = userRepository.findByRole(User.Role.USER);
        return users.stream()
                .map(user ->
                        new UserInfo(user.getUsername(), user.getPhoneNumber(), user.getProfilePicture())
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<UserInfo> viewAdmins(int adminId) {
        if (!isAdmin(adminId)) throw new AdminException("User is not authorized to perform this action");
        List<User> admins = userRepository.findByRole(User.Role.ADMIN);
        return admins.stream()
                .map(admin ->
                        new UserInfo(admin.getUsername(), admin.getPhoneNumber(), admin.getProfilePicture())
                )
                .collect(Collectors.toList());
    }

    @Override
    public boolean promoteUser(int adminId, int userId) {
        if (!isAdmin(adminId)) throw new AdminException("User is not authorized to perform this action");

        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (user.getRole() == User.Role.ADMIN) {
                throw new AdminException("User is already an admin");
            }
            user.setRole(User.Role.ADMIN);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public UserInfo searchByUsername(int adminId, String username) {
        if (!isAdmin(adminId)) throw new AdminException("User is not authorized to perform this action");

        User user = userRepository.findByUsername(username);
        if (user == null) throw new UserException("Username is not found");
        return new UserInfo(user.getUsername(), user.getPhoneNumber(), user.getProfilePicture());
    }
}