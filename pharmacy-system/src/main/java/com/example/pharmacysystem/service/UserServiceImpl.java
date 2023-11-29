package com.example.pharmacysystem.service;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public Boolean currentUserEmail(String email) {
        List<String> usernames = getUsernames();
        if (usernames.contains(email)) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> getUsernames() {
        List<User> users = userRepository.findAll();
        List<String> usernames = users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        return usernames;
    }

}