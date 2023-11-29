package com.example.pharmacysystem.controllers;

import com.example.pharmacysystem.controller.GoogleOAuthController;
import com.example.pharmacysystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GoogleOAuthController.class)
class GoogleOAuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testCreateUserNewUser() throws Exception {
        // Mock the userService.saveUser method
        when(userService.findAllUsers()).thenReturn(List.of());

        // Mock the payload data
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "newuser@example.com");
        payload.put("picture", "user_picture_url");

        // Perform the POST request
        ResultActions result = mockMvc.perform(post("/login/oauth2/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(payload)))
                .andExpect(status().isOk())
                .andExpect(content().string("New User added newuser@example.com"));
    }

    @Test
    void testCreateUserExistingUser() throws Exception {
        // Mock the userService.saveUser method
        when(userService.findAllUsers()).thenReturn(List.of("existinguser@example.com"));

        // Mock the payload data
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "existinguser@example.com");
        payload.put("picture", "user_picture_url");

        // Perform the POST request
        mockMvc.perform(post("/login/oauth2/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(payload)))
                .andExpect(status().isOk())
                .andExpect(content().string("Existing User existinguser@example.com"));
    }

    // Utility method to convert an object to JSON string
    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
