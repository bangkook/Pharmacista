package com.example.pharmacysystem.services;

import com.example.pharmacysystem.exceptions.AdminException;
import com.example.pharmacysystem.model.UserInfo;
import com.example.pharmacysystem.repository.UserRepository;
import com.example.pharmacysystem.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import com.example.pharmacysystem.model.User;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringJUnitConfig
@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void viewUsers_Successfully(){
        // Arrange
        User admin =
                new User("admin1", "pass1", "address1", "city1", "country1", "1234", "01271676366", null);
        admin.setRole(User.Role.ADMIN);
        // Mocking userRepository behavior
        when(userRepository.findByRole(User.Role.USER)).thenReturn(Arrays.asList(
                new User("user1", "pass1", "address1", "city1", "country1", "1234", "01271676366", null),
                new User("user2", "pass2", "address2", "city2", "country2", "1234", "01234567890", null)
        ));
        when(userRepository.findById(1)).thenReturn(
                Optional.of(admin)
        );

        // Act
        List<UserInfo> userInfos = adminService.manageUsers(1);

        // Assert
        assertEquals(2, userInfos.size());
        assertEquals("user1", userInfos.get(0).getUsername());
        assertEquals("01271676366", userInfos.get(0).getPhoneNumber());
        assertEquals("user2", userInfos.get(1).getUsername());
        assertEquals("01234567890", userInfos.get(1).getPhoneNumber());
    }
    @Test
    public void viewUsers_UnauthorizedUser(){
        // Arrange
        User regularUser =
                new User("user1", "pass1", "address1", "city1", "country1", "1234", "01234567890", null);
        regularUser.setRole(User.Role.USER); // this is a confirmation but it can be removed
        // Mocking userRepository behavior
        when(userRepository.findById(1)).thenReturn(
                Optional.of(regularUser)
        );

        // Act & Assert
        assertThrows(AdminException.class, () -> adminService.manageUsers(1));
    }

    @Test
    public void viewAdmins_Successfully(){
        // Arrange
        User admin =
                new User("admin1", "pass1", "address1", "city1", "country1", "1234", "01271676345", null);
        admin.setRole(User.Role.ADMIN);
        // Mocking userRepository behavior
        when(userRepository.findByRole(User.Role.ADMIN)).thenReturn(Arrays.asList(
                new User("admin2", "pass2", "address2", "city2", "country2", "1234", "01271676366", null),
                new User("admin3", "pass3", "address3", "city3", "country3", "1234", "01234567890", null)
        ));
        when(userRepository.findById(1)).thenReturn(
                Optional.of(admin)
        );

        // Act
        List<UserInfo> adminInfos = adminService.getAdmins(1);

        // Assert
        assertEquals(2, adminInfos.size());
        assertEquals("admin2", adminInfos.get(0).getUsername());
        assertEquals("01271676366", adminInfos.get(0).getPhoneNumber());
        assertEquals("admin3", adminInfos.get(1).getUsername());
        assertEquals("01234567890", adminInfos.get(1).getPhoneNumber());
    }

    @Test
    public void viewAdmins_UnauthorizedUser(){
        // Arrange
        User regularUser =
                new User("user1", "pass1", "address1", "city1", "country1", "1234", "01234567890", null);
        regularUser.setRole(User.Role.USER); // this is a confirmation but it can be removed
        // Mocking userRepository behavior
        when(userRepository.findById(1)).thenReturn(
                Optional.of(regularUser)
        );

        // Act & Assert
        assertThrows(AdminException.class, () -> adminService.getAdmins(1));
    }

    @Test
    public void promoteUser_Successfully(){
        // Arrange
        User admin =
                new User("admin1", "pass1", "address1", "city1", "country1", "1234", "01271676366", null);
        admin.setRole(User.Role.ADMIN);
        User userToPromote = new User("user1", "pass1", "address1", "city1", "country1", "1234", "01271632366", null);
        // Mocking userRepository behavior
        when(userRepository.findById(1)).thenReturn(
                Optional.of(admin)
        );
        when(userRepository.findById(2)).thenReturn(
                Optional.of(userToPromote)
        );

        // Act
        boolean result = adminService.promoteUser(1, 2);

        // Assert
        assertEquals(result, true);
        assertEquals(User.Role.ADMIN, userToPromote.getRole());
    }

    @Test
    public void promoteUser_UserNotAuthorized(){
        // Arrange
        User regularUser =
                new User("user1", "pass1", "address1", "city1", "country1", "1234", "01234567890", null);
        regularUser.setRole(User.Role.USER);
        // Mocking userRepository behavior
        when(userRepository.findById(1)).thenReturn(
                Optional.of(regularUser)
        );

        // Act & Assert
        assertThrows(AdminException.class, () -> adminService.promoteUser(1, 2));
    }

    @Test
    public void promoteUser_UserIsAlreadyAdmin(){
        // Arrange
        User admin =
                new User("admin1", "pass1", "address1", "city1", "country1", "1234", "01271676366", null);
        admin.setRole(User.Role.ADMIN);
        User adminToPromote = new User("admin2", "pass2", "address2", "city2", "country2", "1234", "01234567890", null);
        adminToPromote.setRole(User.Role.ADMIN);
        // Mocking userRepository behavior
        when(userRepository.findById(1)).thenReturn(
                Optional.of(admin)
        );
        when(userRepository.findById(2)).thenReturn(
                Optional.of(adminToPromote)
        );

        // Act & Assert
        assertThrows(AdminException.class, () -> adminService.promoteUser(1, 2));
    }

    @Test
    public void searchByUsername_Successfully(){
        // Arrange
        User admin =
                new User("admin1", "pass1", "address1", "city1", "country1", "1234", "01271676366", null);
        admin.setRole(User.Role.ADMIN);
        User userToFind = new User("user1", "pass1", "address1", "city1", "country1", "1234", "01271676366", null);
        // Mocking userRepository behavior
        when(userRepository.findById(1)).thenReturn(
                Optional.of(admin)
        );
        when(userRepository.findByUsername("user1")).thenReturn(userToFind);

        // Act
        UserInfo userInfo = adminService.searchByUsername(1, "user1");

        // Assert
        assertNotEquals(userInfo, null);
        assertEquals("user1", userInfo.getUsername());
        assertEquals("01271676366", userInfo.getPhoneNumber());
    }

    @Test
    public void searchByUsername_NotAuthorized(){
        // Arrange
        User regularUser =
                new User("user1", "pass1", "address1", "city1", "country1", "1234", "01234567890", null);
        regularUser.setRole(User.Role.USER);
        // Mocking userRepository behavior
        when(userRepository.findById(1)).thenReturn(
                Optional.of(regularUser)
        );

        // Act & Assert
        assertThrows(AdminException.class, () -> adminService.searchByUsername(1, "user1"));
    }

    @Test
    public void searchByUsername_UsernameNotFound() {
        // Arrange
        User admin =
                new User("admin1", "pass1", "address1", "city1", "country1", "1234", "01271676366", null);
        admin.setRole(User.Role.ADMIN);
        // Mocking userRepository behavior
        when(userRepository.findById(1)).thenReturn(
                Optional.of(admin)
        );
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(null);

        // Act & Assert
         UserInfo userInfo = adminService.searchByUsername(1, "nonexistentUser");
         assertEquals(userInfo, null);
    }
}
