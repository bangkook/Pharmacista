package com.example.pharmacysystem.Controllers;

import com.example.pharmacysystem.controller.GoogleOAuthSignInController;
import com.example.pharmacysystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserControllerTest {

    @Autowired
    private GoogleOAuthSignInController userController;

    @MockBean
    private UserService userService;

    @Test
    void testCheckUserExistWhenUserExists() throws Exception {
        String email = "test@example.com";
        when(userService.currentUserEmail(email)).thenReturn(true); //This is simulating the case where the user exists.

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); //testing the userController in isolation

        MvcResult result = mockMvc.perform(get("/user/login")
                        .param("email", email))
                        .andExpect(status().isOk())
                        .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("true", content);
    }

    @Test
    void testCheckUserExistWhenUserDoesNotExist() throws Exception {
        String email = "nonexistent@example.com";
        when(userService.currentUserEmail(email)).thenReturn(false);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        MvcResult result = mockMvc.perform(get("/user/login")
                        .param("email", email))
                        .andExpect(status().isOk())
                        .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("false", content);
    }
}
