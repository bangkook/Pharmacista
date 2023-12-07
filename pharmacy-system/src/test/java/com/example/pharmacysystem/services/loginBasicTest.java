package com.example.pharmacysystem.services;

import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import com.example.pharmacysystem.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class loginBasicTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void getUser_ReturnWantedUser() {
        User firstUser = new User("eman", "123", "16 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");
        User secondUser = new User("Neso", "3211",
                "15 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");
        List<User> Users = List.of(firstUser, secondUser);
        Mockito.when(userRepository.findAll()).thenReturn(Users);

        User getUser = userService.getUser("eman", "123");
        assertEquals(getUser, firstUser);
    }

    @Test
    public void getUser_ReturnNullAsUserNotFound() {
        User firstUser = new User("eman", "123", "16 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");
        User secondUser = new User("Neso", "3211",
                "15 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");

        List<User> Users = List.of(firstUser, secondUser);
        Mockito.when(userRepository.findAll()).thenReturn(Users);

        User getUser = userService.getUser("Maria", "1423");
        assertNull(getUser);
    }

    @Test
    public void getUser_ReturnNullAsUserNameNull() {
        User firstUser = new User("eman", "123", "16 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");
        User secondUser = new User("Neso", "3211",
                "15 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");

        List<User> Users = List.of(firstUser, secondUser);
        Mockito.when(userRepository.findAll()).thenReturn(Users);

        User getUser = userService.getUser(null, "1423");
        assertNull(getUser);
    }

    @Test
    public void getUser_ReturnNullAsPasswordNull() {
        User firstUser = new User("eman", "123", "16 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");
        User secondUser = new User("Neso", "3211",
                "15 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");

        List<User> Users = List.of(firstUser, secondUser);
        Mockito.when(userRepository.findAll()).thenReturn(Users);

        User getUser = userService.getUser("eman", null);
        assertNull(getUser);
    }

    @Test
    public void getUser_ReturnNullAsUserNamePasswordNull() {
        User firstUser = new User("eman", "123", "16 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");
        User secondUser = new User("Neso", "3211",
                "15 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");

        List<User> Users = List.of(firstUser, secondUser);
        Mockito.when(userRepository.findAll()).thenReturn(Users);

        User getUser = userService.getUser(null, null);
        assertNull(getUser);
    }

    @Test
    public void checkUser_ReturnUserNameAndPasswordCorrect() {
        User firstUser = new User("eman", "123", "16 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");
        User secondUser = new User("Neso", "3211",
                "15 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");
        List<User> Users = List.of(firstUser, secondUser);
        Mockito.when(userRepository.findAll()).thenReturn(Users);

        UserService.LoginStatus loginStatus = userService.checkUser("eman", "123");
        assertEquals(loginStatus, UserService.LoginStatus.USER_FOUND_CORRECT_PASSWORD);
    }

    @Test
    public void checkUser_ReturnUserNameNotfound() {
        User firstUser = new User("eman", "123", "16 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");
        User secondUser = new User("Neso", "3211",
                "15 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");

        List<User> Users = List.of(firstUser, secondUser);
        Mockito.when(userRepository.findAll()).thenReturn(Users);

        UserService.LoginStatus loginStatus  = userService.checkUser("Maria", "1423");
        assertEquals(loginStatus, UserService.LoginStatus.USER_NOT_FOUND);
    }

    @Test
    public void checkUser_ReturnWrongPassword() {
        User firstUser = new User("eman", "123", "16 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");
        User secondUser = new User("Neso", "3211",
                "15 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");

        List<User> Users = List.of(firstUser, secondUser);
        Mockito.when(userRepository.findAll()).thenReturn(Users);

        UserService.LoginStatus loginStatus  = userService.checkUser("eman", "666");
        assertEquals(loginStatus, UserService.LoginStatus.USER_FOUND_INCORRECT_PASSWORD);
    }

    @Test
    public void checkUser_ReturnNullPasswordOrNameOrBoth() {
        User firstUser = new User("eman", "123", "16 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");
        User secondUser = new User("Neso", "3211",
                "15 avennue", "newYork",
                "usa", "1245", "01123186969",
                "profile_picture_url");

        List<User> Users = List.of(firstUser, secondUser);
        Mockito.when(userRepository.findAll()).thenReturn(Users);

        UserService.LoginStatus loginStatus1  = userService.checkUser(null, "666");
        assertEquals(loginStatus1, UserService.LoginStatus.INVALID_INPUT);
        UserService.LoginStatus loginStatus2 = userService.checkUser("eman", null);
        assertEquals(loginStatus2, UserService.LoginStatus.INVALID_INPUT);
        UserService.LoginStatus loginStatus3  = userService.checkUser(null, null);
        assertEquals(loginStatus3, UserService.LoginStatus.INVALID_INPUT);
    }
}

