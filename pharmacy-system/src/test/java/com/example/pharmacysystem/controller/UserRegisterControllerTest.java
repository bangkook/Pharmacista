package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.exceptions.UserRegistrationException;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
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
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRegisterControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void addUser_Success() throws Exception {
        User user = new User("username", "pass", "address", "city", "country", "zip", "01271676366", null);
        given(userService.saveUser(user)).willReturn(user);

        MvcResult res = mockMvc.perform(post("/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();

        String resultContent = res.getResponse().getContentAsString();
        String expectedResponse = "New User is added successfully";
        Assert.assertTrue(resultContent.equals(expectedResponse));
    }

    @Test
    public void addUser_Fail_DuplicateUserNames() throws Exception {
        String errorMessage = "Username is already taken. Choose another one!";
        User user = new User("username", "pass", "address", "city", "country", "zip", "01271676366", null);
        doThrow(new UserRegistrationException(errorMessage))
                .when(userService).saveUser(any(User.class));

        MvcResult res = mockMvc.perform(post("/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String resultContent = res.getResponse().getContentAsString();
        Assert.assertEquals(errorMessage, resultContent);
    }
    @Test
    public void addUser_Fail_InvalidUsername() throws Exception {
        String errorMessage = "Invalid username. Please follow the specified constraints.";
        User user = new User("2username", "pass", "address", "city", "country", "zip", "01271676366", null);
        doThrow(new UserRegistrationException(errorMessage))
                .when(userService).saveUser(any(User.class));

        MvcResult res = mockMvc.perform(post("/user/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String resultContent = res.getResponse().getContentAsString();
        Assert.assertTrue(resultContent.equals(errorMessage));
    }
}
