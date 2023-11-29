package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

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


    @Override
    public boolean changePassword(int id, String currentPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent() && newPassword != null) {
            User user = optionalUser.get();
            if (!(currentPassword.equals(user.getPassword())))
                return false;

            // Update user data
            user.setPassword(newPassword);

            // Save the updated user to the database
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public List<String> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<String> usernames = users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        return usernames;
    }

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


