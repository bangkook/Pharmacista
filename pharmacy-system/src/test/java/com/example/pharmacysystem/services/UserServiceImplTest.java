package com.example.pharmacysystem.services;

import com.example.pharmacysystem.exceptions.UserRegistrationException;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import com.example.pharmacysystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void saveUser_ValidUser_ReturnsSavedUser() {
        // Mock User
        User newUser = new User("username", "pass", "address", "city", "country", "zip", "01271676366", null);
        Mockito.when(userRepository.save(newUser)).thenReturn(newUser);
        Mockito.when(userRepository.findByUsername(newUser.getUsername())).thenReturn(null);
        User savedUser = userService.saveUser(newUser);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo(newUser.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(newUser.getPassword());
    }

    @Test
    public void saveUser_DuplicateUsername_ThrowsUserRegistrationException() {
        // Mock User
        User newUser = new User("username", "pass", "address", "city", "country", "zip", "01271676366", null);
        Mockito.when(userRepository.save(newUser)).thenReturn(newUser);
        Mockito.when(userRepository.findByUsername(newUser.getUsername())).thenReturn(newUser);

        try {
            userService.saveUser(newUser);
        } catch (UserRegistrationException e) {
            // Assert specific details about the exception if needed
            assertEquals("Username is already taken. Choose another one!", e.getMessage());
        }
    }

    @Test
    public void InvalidUsername_ReturnsFalse() {
        // Mock User
        User newUser = new User("1username", "pass", "address", "city", "country", "zip", "01271676366", null);
        Mockito.when(userRepository.save(newUser)).thenReturn(newUser);
        Mockito.when(userRepository.findByUsername(newUser.getUsername())).thenReturn(null);
        try {
            userService.saveUser(newUser);
        } catch (UserRegistrationException e) {
            // Assert specific details about the exception if needed
            assertEquals("Invalid username. Please follow the specified constraints.", e.getMessage());
        }
    }

}
