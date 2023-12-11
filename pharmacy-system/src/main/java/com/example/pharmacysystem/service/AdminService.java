package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    List<UserInfo> viewUsers(int adminId);

    List<UserInfo> viewAdmins(int adminId);

    boolean promoteUser(int adminId, int userId);

    UserInfo searchByUsername(int adminId, String username);
}