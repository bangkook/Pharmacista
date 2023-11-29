package com.example.pharmacysystem.service;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class GoogleOAuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testSaveUser() {
        // Arrange
        User newUser = new User(1, "newuser@example.com", null, null, null, null, null, null, "profile_picture_url");
        Mockito.when(userRepository.save(newUser)).thenReturn(newUser);

        // Act
        User savedUser = userService.saveUser(newUser);

        // Assert
        assertEquals(newUser, savedUser);
    }

    @Test
    void testFindAllUsers() {
        // Arrange
        List<User> users = Arrays.asList(
                new User(1, "user1@example.com", null, null, null, null, null, null, "profile_picture_url1"),
                new User(2, "user2@example.com", null, null, null, null, null, null, "profile_picture_url2")
        );
        Mockito.when(userRepository.findAll()).thenReturn(users);

        // Act
        List<String> usernames = userService.findAllUsers();

        // Assert
        assertEquals(Arrays.asList("user1@example.com", "user2@example.com"), usernames);
    }


}
