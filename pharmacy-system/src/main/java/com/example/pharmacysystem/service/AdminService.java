package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminService {

    List<UserInfo> manageUsers(int adminId);

    List<UserInfo> getAdmins(int adminId);

    boolean promoteUser(int adminId, int userId);

    UserInfo searchByUsername(int adminId, String username);
}