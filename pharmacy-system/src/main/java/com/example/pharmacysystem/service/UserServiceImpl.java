package com.example.pharmacysystem.service;

import com.example.pharmacysystem.exceptions.UserRegistrationException;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        boolean found = isUsernameFound(user.getUsername());
        if (found) {
            throw new UserRegistrationException("Username is already taken. Choose another one!");
        }
        if (!isValidUsername(user.getUsername())) {
            throw new UserRegistrationException("Invalid username. Please follow the specified constraints.");
        }
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserRegistrationException("An error occurred while saving the user.");
        }
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
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
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
    public boolean isUsernameFound(String username) {
        // Check if the username already exists in the database
        return userRepository.findByUsername(username) != null;
    }
    private boolean isValidUsername(String username) {
        // Validating the username with 6 to 30 characters,
        // starting with an alphabetical character, followed by alphanumeric characters and underscores.
        // Disallowed: Special characters at the beginning, spaces.
        // Example: "John_Doe123"

        String regex = "^[A-Za-z]\\w{5,29}$";        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);

        // Return if the username matched the Regex
        return m.matches();
    }
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


