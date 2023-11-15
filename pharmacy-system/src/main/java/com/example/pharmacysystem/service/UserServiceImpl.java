package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean updateUserData(int id, String streetAddress, String city, String country, String zipCode, String phone) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Update user data
            user.setStreetAddress(streetAddress);
            user.setCity(city);
            user.setCountry(country);
            user.setZipCode(zipCode);
            user.setPhoneNumber(phone);

            // Save the updated user to the database
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean uploadProfilePicture(int id, String profilePicture) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent() && profilePicture != null) {
            User user = optionalUser.get();
            // Update user data
            user.setProfilePicture(profilePicture);

            // Save the updated user to the database
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override

    public boolean changePassword(int id, String currentPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent() && newPassword != null) {
            User user = optionalUser.get();
            if (!encoder().matches(currentPassword, user.getPassword()))
                return false;

            // Update user data
            user.setPassword(encoder().encode(newPassword));

            // Save the updated user to the database
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
