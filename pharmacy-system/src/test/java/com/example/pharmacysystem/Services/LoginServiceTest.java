package com.example.pharmacysystem.Services;

import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import com.example.pharmacysystem.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testUserSuccessfulLogin() {
        String email = "es-nisreenhisham2025@alexu.edu.eg";

        // Mock the behavior of userRepository.findAll()
        when(userRepository.findAll()).thenReturn(
                List.of(
                        new User(2, "es-nisreen@alexu.edu.eg", "password", "old address", "old city",
                                "old country", "11111", "12345678911", "picture.jpg"),
                        new User(1, "es-nisreenhisham2025@alexu.edu.eg", "password", "old address", "old city",
                                "old country", "11111", "12345678911", "picture.jpg")
                )
        );

        // Perform the test
        boolean result = userService.currentUserEmail(email);

        assertTrue(result);
    }


    @Test
    public void testUserFailedLogin() {
        String email = "nancy@alexu.edu.eg";
        // Mock the behavior of userRepository.findAll()
        when(userRepository.findAll()).thenReturn(
                List.of(
                        new User(2, "es-nisreen@alexu.edu.eg", "password", "old address", "old city",
                                "old country", "11111", "12345678911", "picture.jpg"),
                        new User(1, "es-nisreenhisham2025@alexu.edu.eg", "password", "old address", "old city",
                                "old country", "11111", "12345678911", "picture.jpg")
                )
        );

        // Perform the test
        boolean result = userService.currentUserEmail(email);
        assertFalse(result);
    }

    @Test
    public void testGetEmails() {

        // Mock the behavior of userRepository.findAll()
        when(userRepository.findAll()).thenReturn(
                List.of(
                        new User(2, "es-nisreen@alexu.edu.eg", "password", "old address", "old city",
                                "old country", "11111", "12345678911", "picture.jpg"),
                        new User(1, "es-nisreenhisham2025@alexu.edu.eg", "password", "old address", "old city",
                                "old country", "11111", "12345678911", "picture.jpg")
                )
        );

        // Perform the test
        List<String> usernames = userService.getUsernames();

        assertTrue(usernames.contains("es-nisreen@alexu.edu.eg"));
        assertTrue(usernames.contains("es-nisreenhisham2025@alexu.edu.eg"));
    }
}
