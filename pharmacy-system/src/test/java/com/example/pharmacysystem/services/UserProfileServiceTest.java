package com.example.pharmacysystem.services;

import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import com.example.pharmacysystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserProfileServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testGetUserData_Successfully() {
        // Mock data
        int userId = 1;
        when(userRepository.findById(userId))
                .thenReturn(java.util.Optional.of(new User("username", "password", "address", "city",
                        "country", "11111", "12345678911", "picture.jpg")));

        // Assert no exception is thrown
        assertAll(() -> userService.getUserById(userId));

        User user = userService.getUserById(userId);
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("address", user.getStreetAddress());
        assertEquals("city", user.getCity());
        assertEquals("country", user.getCountry());
        assertEquals("11111", user.getZipCode());
        assertEquals("12345678911", user.getPhoneNumber());
        assertEquals("picture.jpg", user.getProfilePicture());
    }

    @Test
    void testGetUserData_UserNotFound() {
        // Mock data
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // Assert runtime exception is thrown
        assertThrows(RuntimeException.class, () -> userService.getUserById(userId));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdateUserData_Successfully() {
        // Mock data
        int userId = 1;
        User user = new User("username", "password", "old address", "old city",
                "old country", "11111", "12345678911", "picture.jpg");
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        // Perform the test
        boolean dataUpdated = userService.updateUserData(userId, "new address", "new city",
                "new country", "22222", "98765432111");

        assertTrue(dataUpdated);
        assertEquals("new address", user.getStreetAddress());
        assertEquals("new city", user.getCity());
        assertEquals("new country", user.getCountry());
        assertEquals("22222", user.getZipCode());
        assertEquals("98765432111", user.getPhoneNumber());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserData_UserNotFound() {
        // Mock data
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // Perform the test
        boolean dataUpdated = userService.updateUserData(userId, "new address", "new city",
                "new country", "22222", "98765432111");

        assertFalse(dataUpdated);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testChangePassword_Successfully() {
        // Mock data
        int userId = 1;
        String encodedPass = "currentPassword";
        User existingProfile = new User("username", encodedPass, "street address", "city",
                "country", "11111", "12345678911", "picture.jpg");
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingProfile));

        boolean passwordChanged = userService.changePassword(userId, "currentPassword", "newPassword");

        assertTrue(passwordChanged);
        // Additional assertions to verify that the password has been changed in the system
        assertEquals(("newPassword"), existingProfile.getPassword());
    }

    @Test
    void testChangePassword_CurrentPasswordIsWrong() {
        // Mock data
        int userId = 1;
        String encodedPass = "currentPassword";
        User existingProfile = new User("username", encodedPass, "street address", "city",
                "country", "11111", "12345678911", "picture.jpg");
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingProfile));

        boolean passwordChanged = userService.changePassword(userId, "wrongPassword", "newPassword");

        assertFalse(passwordChanged);
        // Additional assertions to verify that the password hasn't been changed in the system
        assertEquals(encodedPass, existingProfile.getPassword());
    }

    @Test
    void testChangePassword_GivenPasswordIsNull() {
        // Mock data
        int userId = 1;
        String encodedPass = "currentPassword";
        User existingProfile = new User("username", encodedPass, "street address", "city",
                "country", "11111", "12345678911", "picture.jpg");
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingProfile));

        boolean passwordChanged = userService.changePassword(userId, "currentPassword", null);

        assertFalse(passwordChanged);
        // Additional assertions to verify that the password hasn't been changed in the system
        assertEquals(encodedPass, existingProfile.getPassword());
    }

    @Test
    void testUploadProfilePicture_Successfully() {
        // Mock data
        int userId = 1;
        User existingProfile = new User("username", "password", "street address", "city",
                "country", "11111", "12345678911", "oldPicture.jpg");
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingProfile));

        boolean pictureUploaded = userService.uploadProfilePicture(userId, "newPicture.jpg");

        assertTrue(pictureUploaded);
        // Additional assertions to verify that the user's profile picture has been updated
        assertEquals("newPicture.jpg", existingProfile.getProfilePicture());
    }

    @Test
    void testUploadProfilePicture_PictureIsNull() {
        // Mock data
        int userId = 1;
        User existingProfile = new User("username", "password", "street address", "city",
                "country", "11111", "12345678911", "oldPicture.jpg");
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingProfile));

        boolean pictureUploaded = userService.uploadProfilePicture(userId, null);

        assertFalse(pictureUploaded);
        // Additional assertions to verify that the user's profile picture hasn't been updated
        assertEquals("oldPicture.jpg", existingProfile.getProfilePicture());
    }

}
