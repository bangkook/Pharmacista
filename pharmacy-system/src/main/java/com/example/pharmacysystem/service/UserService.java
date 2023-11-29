package com.example.pharmacysystem.service;
import com.example.pharmacysystem.model.User;
import java.util.List;

public interface UserService {
    List<String> getUsernames();
    Boolean currentUserEmail(String email);

}
