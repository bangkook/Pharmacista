package com.example.pharmacysystem.service;

import com.example.pharmacysystem.exceptions.UserException;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.model.UserBuilder;
import com.example.pharmacysystem.repository.UserRepository;
import com.example.pharmacysystem.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.pharmacysystem.utils.Constants.EMPTY_IMAGE;

@Primary
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new PasswordEncoder();

    @Override
    public User saveUser(User user) {
        boolean found = isUsernameFound(user.getUsername());
        if (found) {
            throw new UserException("Username is already taken. Choose another one!");
        }
        if (!isValidUsername(user.getUsername())) {
            throw new UserException("Invalid username. Please follow the specified constraints.");
        } else if (!isValidPassword(user.getPassword())) {
            throw new UserException("Invalid password. Please follow the specified constraints.");
        } else if (!isValidZip(user.getZipCode())) {
            throw new UserException("Invalid zipcode. Please follow the specified constraints.");
        } else if (!isValidPhone(user.getPhoneNumber())) {
            throw new UserException("Invalid phone number. Please follow the specified constraints.");
        }
        // Set the role explicitly
        user.setRole(User.Role.USER);
        try {
            user.setRole(User.Role.USER);
            user.setProfilePicture(EMPTY_IMAGE);
            String encryptedPass = passwordEncoder.encryptPass(user.getPassword());
            user.setPassword(encryptedPass);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserException("An error occurred while saving the user.");
        }
    }

    @Override
    public User saveUserGoogle(User user) {
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

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();

        // Update user data
        user = new UserBuilder(user)
                .buildAddress(streetAddress, city, country, zipCode)
                .buildPhoneNumber(phone)
                .build();

        // Save the updated user to the database
        userRepository.save(user);
        return true;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean uploadProfilePicture(int id, String profilePicture) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty() || profilePicture == null) {
            return false;
        }

        User user = optionalUser.get();
        // Update user data
        user = new UserBuilder(user).buildProfilePicture(profilePicture).build();

        // Save the updated user to the database
        userRepository.save(user);
        return true;
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

    public boolean isValidPhone(String phone) {
        return phone == null || phone.isEmpty() || Pattern.matches("^\\d{11}$", phone);
    }

    public static boolean isValidPassword(String pass) {
        return pass == null || Pattern.matches("^.{8,16}$", pass);
    }

    public static boolean isValidZip(String zip) {
        return zip == null || zip.isEmpty() || Pattern.matches("^\\d{3,5}$", zip);
    }

    @Override
    public boolean changePassword(int id, String currentPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty() || newPassword == null) {
            return false;
        }

        User user = optionalUser.get();
        if (!(passwordEncoder.isEqual(currentPassword, user.getPassword())))
            return false;

        // Update user data
        user = new UserBuilder(user).buildPassword(newPassword).build();

        // Save the updated user to the database
        userRepository.save(user);
        return true;
    }

    @Override
    public List<String> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean currentUserEmail(String email) {
        List<String> usernames = getUsernames();
        return usernames.contains(email);
    }

    @Override
    public List<String> getUsernames() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

    @Override
    public User getUser(String userName, String password) {
        List<User> Users = userRepository.findAll();
        if (userName == null || password == null) return null;
        User user = null;
        for (User u : Users) {
            if (u.getUsername().equals(userName) && u.getPassword().equals(new PasswordEncoder().encryptPass(password))) {
                user = u;
                break;
            }
        }
        return user;
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public LoginStatus checkUser(String userName, String password) {
        List<User> Users = userRepository.findAll();
        if (userName == null || password == null) return LoginStatus.INVALID_INPUT;
        for (User u : Users) {
            if (u.getUsername().equals(userName)) {
                if (u.getPassword().equals(new PasswordEncoder().encryptPass(password))) {
                    return LoginStatus.USER_FOUND_CORRECT_PASSWORD;
                }
                return LoginStatus.USER_FOUND_INCORRECT_PASSWORD;
            }
        }
        return LoginStatus.USER_NOT_FOUND;
    }

    @Override
    public boolean isAdmin(int adminId) {
        User admin = userRepository.findById(adminId).orElse(null);
        if (admin == null) return false;
        return admin.getRole() == User.Role.ADMIN;
    }
}


