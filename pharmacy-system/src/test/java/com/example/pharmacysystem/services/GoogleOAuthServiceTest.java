package com.example.pharmacysystem.services;

import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.UserRepository;
import com.example.pharmacysystem.service.UserService;
import com.example.pharmacysystem.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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
        User newUser = new User("newuser", null, null, null, null, null, null, "profile_picture_url");
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
                new User("user1@example.com", null, null, null, null, null, null, "profile_picture_url1"),
                new User("user2@example.com", null, null, null, null, null, null, "profile_picture_url2")
        );
        Mockito.when(userRepository.findAll()).thenReturn(users);

        // Act
        List<String> usernames = userService.findAllUsers();

        // Assert
        assertEquals(Arrays.asList("user1@example.com", "user2@example.com"), usernames);
    }


    @RunWith(SpringRunner.class)
    @SpringBootTest
    public static class logInServiceTest {
        @Autowired
        private UserService userService;
        @MockBean
        private UserRepository userRepository;

        @org.junit.Test
        public void getUser_ReturnWantedUser(){
            User firstUser=new User("eman", "123", "16 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");
            User secondUser=new User("Neso", "3211",
                    "15 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");
            List<User> Users=List.of( firstUser , secondUser);
            Mockito.when(userRepository.findAll()).thenReturn(Users);

            User getUser=userService.getUser("eman","123");
            assertEquals(getUser,firstUser);
        }

        @org.junit.Test
        public void getUser_ReturnNullAsUserNotFound(){
            User firstUser=new User("eman", "123", "16 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");
            User secondUser=new User("Neso", "3211",
                    "15 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");

            List<User> Users=List.of( firstUser , secondUser);
            Mockito.when(userRepository.findAll()).thenReturn(Users);

            User getUser=userService.getUser("Maria","1423");
            assertEquals(getUser,null);
        }
        @org.junit.Test
        public void getUser_ReturnNullAsUserNameNull(){
            User firstUser=new User("eman", "123", "16 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");
            User secondUser=new User("Neso", "3211",
                    "15 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");

            List<User> Users=List.of( firstUser , secondUser);
            Mockito.when(userRepository.findAll()).thenReturn(Users);

            User getUser=userService.getUser(null,"1423");
            assertEquals(getUser,null);
        }
        @org.junit.Test
        public void getUser_ReturnNullAsPasswordNull(){
            User firstUser=new User("eman", "123", "16 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");
            User secondUser=new User("Neso", "3211",
                    "15 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");

            List<User> Users=List.of( firstUser , secondUser);
            Mockito.when(userRepository.findAll()).thenReturn(Users);

            User getUser=userService.getUser("eman",null);
            assertEquals(getUser,null);
        }
        @org.junit.Test
        public void getUser_ReturnNullAsUserNamePasswordNull(){
            User firstUser=new User("eman", "123", "16 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");
            User secondUser=new User("Neso", "3211",
                    "15 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");

            List<User> Users=List.of( firstUser , secondUser);
            Mockito.when(userRepository.findAll()).thenReturn(Users);

            User getUser=userService.getUser(null,null);
            assertEquals(getUser,null);
        }

        @org.junit.Test
        public void checkUser_Return1_UserNameAndPasswordCorrect(){
            User firstUser=new User("eman", "123", "16 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");
            User secondUser=new User("Neso", "3211",
                    "15 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");
            List<User> Users=List.of( firstUser , secondUser);
            Mockito.when(userRepository.findAll()).thenReturn(Users);

            int check=userService.checkUser("eman","123");
            assertEquals(check,1);
        }

        @org.junit.Test
        public void checkUser_ReturnNegative1_UserNameNotfound(){
            User firstUser=new User("eman", "123", "16 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");
            User secondUser=new User("Neso", "3211",
                    "15 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");

            List<User> Users=List.of( firstUser , secondUser);
            Mockito.when(userRepository.findAll()).thenReturn(Users);

            int check=userService.checkUser("Maria","1423");
            assertEquals(check,-1);
        }
        @org.junit.Test
        public void checkUser_Return0_wrongPassword(){
            User firstUser=new User("eman", "123", "16 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");
            User secondUser=new User("Neso", "3211",
                    "15 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");

            List<User> Users=List.of( firstUser , secondUser);
            Mockito.when(userRepository.findAll()).thenReturn(Users);

            int check=userService.checkUser("eman","666");
            assertEquals(check,0);
        }
        @org.junit.Test
        public void checkUser_ReturnNegative1_NullPasswordOrNameOrBoth(){
            User firstUser=new User("eman", "123", "16 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");
            User secondUser=new User("Neso", "3211",
                    "15 avennue", "newYork",
                    "usa", "1245","01123186969",
                    "profile_picture_url");

            List<User> Users=List.of( firstUser , secondUser);
            Mockito.when(userRepository.findAll()).thenReturn(Users);

            int check1=userService.checkUser(null,"666");
            assertEquals(check1,-1);
            int check2=userService.checkUser("eman",null);
            assertEquals(check2,-1);
            int check3=userService.checkUser(null,null);
            assertEquals(check3,-1);
        }
    }
}
