package com.example.pharmacysystem.controllers;

import com.example.pharmacysystem.controller.UserController;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class logInControllerTest {

        @MockBean
        private UserService userService;

        @Autowired
        private UserController userController;

        @Test
        public void checkUser_CorrectNameAndPassword() throws Exception {
            String userName = "eman";
            String password = "1234";
            UserService.LoginStatus expectedResult = UserService.LoginStatus.USER_FOUND_CORRECT_PASSWORD;
            when(userService.checkUser(userName, password)).thenReturn(expectedResult);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
            MvcResult result = mockMvc.perform(post("/user/checkUser")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"userName\":\"" + userName + "\",\"password\":\"" + password + "\"}"))
                    .andExpect(status().isOk())
                    .andReturn();

            String content = result.getResponse().getContentAsString();
            assertEquals("USER_FOUND_CORRECT_PASSWORD", content);
        }

        @Test
        public void checkUser_CorrectNameAndIncorrectPassword() throws Exception {
            String userName = "eman";
            String password = "155234";
            UserService.LoginStatus expectedResult = UserService.LoginStatus.USER_FOUND_INCORRECT_PASSWORD;
            when(userService.checkUser(userName, password)).thenReturn(expectedResult);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
            MvcResult result = mockMvc.perform(post("/user/checkUser")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"userName\":\"" + userName + "\",\"password\":\"" + password + "\"}"))
                    .andExpect(status().isOk())
                    .andReturn();

            String content = result.getResponse().getContentAsString();
            assertEquals("USER_FOUND_INCORRECT_PASSWORD", content);
        }

        @Test
        public void checkUser_IncorrectNameAndPassword() throws Exception {
            String userName = "nesoo";
            String password = "155234";
            UserService.LoginStatus expectedResult = UserService.LoginStatus.USER_NOT_FOUND;
            when(userService.checkUser(userName, password)).thenReturn(expectedResult);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
            MvcResult result = mockMvc.perform(post("/user/checkUser")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"userName\":\"" + userName + "\",\"password\":\"" + password + "\"}"))
                    .andExpect(status().isOk())
                    .andReturn();

            String content = result.getResponse().getContentAsString();
            assertEquals("USER_NOT_FOUND", content);
        }

        @Test
        public void getUsersByName_CorrectNameAndPassword() throws Exception {
            String userName = "eman";
            String password = "1234";
            User expectedResult = new User("eman", "123", "16 avennue", "newYork",
                    "usa", "1245", "01123186969",
                    "profile_picture_url");

            when(userService.getUser(userName, password)).thenReturn(expectedResult);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
            MvcResult result = mockMvc.perform(post("/user/getUserByName")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"userName\":\"" + userName + "\",\"password\":\"" + password + "\"}"))
                    .andExpect(status().isOk())
                    .andReturn();

            ObjectMapper mapper = new ObjectMapper();
            String content = result.getResponse().getContentAsString();
            assertEquals(mapper.writeValueAsString(expectedResult), content);
        }

        @Test
        public void getUsersByName_IncorrectNameAndPassword() throws Exception {
            String userName = "nesoo";
            String password = "155234";

            when(userService.getUser(userName, password)).thenReturn(null);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
            MvcResult result = mockMvc.perform(post("/user/getUserByName")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"userName\":\"" + userName + "\",\"password\":\"" + password + "\"}"))
                    .andExpect(status().isOk())
                    .andReturn();

            String content = result.getResponse().getContentAsString();
            assertEquals("", content); // or assert based on your application's behavior for null response
        }
    }
