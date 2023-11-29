package com.example.pharmacysystem.controllers;

import com.example.pharmacysystem.controller.UserController;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class logInControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private UserController userController;

    @Test
    public void CheckUser_CorrectNameAndPassword() throws Exception{
        String userName = "eman";
        String password = "1234";
        int expectedResult = 1;
        when(userService.checkUser(userName, password)).thenReturn(expectedResult);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MvcResult result = mockMvc.perform(get("/user/checkUser")
                        .param("userName", userName)
                        .param("password", password))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("1", content);
//
    }

    @Test
    public void CheckUser_CorrectNameAndIncorrectPassword() throws Exception{
        String userName = "eman";
        String password = "155234";
        int expectedResult = 0;
        when(userService.checkUser(userName, password)).thenReturn(expectedResult);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MvcResult result = mockMvc.perform(get("/user/checkUser")
                        .param("userName", userName)
                        .param("password", password))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("0", content);
//
    }

    @Test
    public void CheckUser_IncorrectNameAndPassword() throws Exception{
        String userName = "nesoo";
        String password = "155234";
        int expectedResult = -1;
        when(userService.checkUser(userName, password)).thenReturn(expectedResult);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MvcResult result = mockMvc.perform(get("/user/checkUser")
                        .param("userName", userName)
                        .param("password", password))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("-1", content);
//
    }

    @Test
    public void getUser_CorrectNameAndPassword() throws Exception{
        String userName = "eman";
        String password = "1234";
        User expectedResult = new User("eman", "123", "16 avennue", "newYork",
                "usa", "1245","01123186969",
                "profile_picture_url");

        when(userService.getUser(userName, password)).thenReturn(expectedResult);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MvcResult result = mockMvc.perform(get("/user/getUserByName")
                        .param("userName", userName)
                        .param("password", password))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        String content = result.getResponse().getContentAsString();
        assertEquals(mapper.writeValueAsString(expectedResult), content);

    }

    @Test
    public void getUser_IncorrectNameAndPassword() throws Exception{
        String userName = "nesoo";
        String password = "155234";
        User expectedResult = null;

        when(userService.getUser(userName, password)).thenReturn(expectedResult);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MvcResult result = mockMvc.perform(get("/user/getUserByName")
                        .param("userName", userName)
                        .param("password", password))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("", content);//null

    }




}

