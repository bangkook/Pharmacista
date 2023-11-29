package com.example.pharmacysystem.service;

import com.example.pharmacysystem.exceptions.UserRegistrationException;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User saveUser(User user) {
        System.out.println("-----------------------------------------");
        System.out.println("In saveUser service");
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        boolean found = isUsernameFound(user.getUsername());
        if (found) {
            throw new UserRegistrationException("Username is already taken. Choose another one!");
        }
        if (!isValidUsername(user.getUsername())) {
            throw new UserRegistrationException("Invalid username. Please follow the specified constraints.");
        }

        try {
            System.out.println("New User is added successfully");
            return userRepository.save(user);
        } catch (Exception e) {
//            e.printStackTrace();
            throw new UserRegistrationException("An error occurred while saving the user.");

        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public boolean isUsernameFound(String username) {
        // Check if the username already exists in the database
        return userRepository.findByUsername(username) != null;
    }
    private boolean isValidUsername(String username) {
        // Validating the username with 6 to 30 characters,
        // starting with an alphabetical character, followed by alphanumeric characters and underscores.
        // Disallowed: Special characters at the beginning, spaces.
        // Example: "John_Doe123"

        String regex = "^[A-Za-z]\\w{5,29}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);

        // Return if the username matched the Regex
        return m.matches();
    }
}
