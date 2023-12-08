package com.example.pharmacysystem.controllers;

import com.example.pharmacysystem.exceptions.UserException;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.model.UserInfo;
import com.example.pharmacysystem.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AdminService adminService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void promoteUserToAdmin_Success() throws Exception {
        int adminId = 1;
        int userId = 2;
        given(adminService.promoteUser(adminId, userId)).willReturn(true);

        MvcResult result = mockMvc.perform(put("/admin/promote-to-admin/{adminId}/{userId}", adminId, userId))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("User promoted to admin successfully", result.getResponse().getContentAsString());
    }

    @Test
    public void promoteUserToAdmin_Fail() throws Exception{
        int adminId = 1;
        int userId = 2;
        given(adminService.promoteUser(adminId, userId)).willReturn(false);

        MvcResult result = mockMvc.perform(put("/admin/promote-to-admin/{adminId}/{userId}", adminId, userId))
                .andExpect(status().isNotFound())
                .andReturn();
        assertEquals("User not found or promotion failed", result.getResponse().getContentAsString());
    }

    @Test
    public void getUsers_Success() throws Exception {
        int adminId = 1;

        UserInfo firstUser = new UserInfo("Mary_Alfred", "01234567890", "pic1");
        UserInfo secondUser = new UserInfo("John_Doe", "01237894560", "pic2");
        List<UserInfo> userInfoList = List.of(firstUser, secondUser);

        given(adminService.viewUsers(adminId)).willReturn(userInfoList);
        MvcResult result = mockMvc.perform(get("/admin/{adminId}/findUsers", adminId))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(objectMapper.writeValueAsString(userInfoList), result.getResponse().getContentAsString());
    }

    @Test
    public void getAdmins_Success() throws Exception {
        int adminId = 1;

        UserInfo firstUser = new UserInfo("Mary_Alfred", "01234567890", "pic1");
        UserInfo secondUser = new UserInfo("John_Doe", "01237894560", "pic2");
        List<UserInfo> adminInfoList = List.of(firstUser, secondUser);

        given(adminService.viewAdmins(adminId)).willReturn(adminInfoList);
        MvcResult result = mockMvc.perform(get("/admin/{adminId}/findAdmins", adminId))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(objectMapper.writeValueAsString(adminInfoList), result.getResponse().getContentAsString());
    }

    @Test
    public void searchByUsername_Success() throws Exception {
        int adminId = 1;
        String username = "testUser";

        UserInfo u = new UserInfo("testUser", "01234567890", "pic1");
        given(adminService.searchByUsername(adminId, username)).willReturn(u);
        MvcResult result = mockMvc.perform(get("/admin/searchUser/{adminId}/{username}", adminId, username))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(objectMapper.writeValueAsString(u), result.getResponse().getContentAsString());
    }

    @Test
    public void searchByUsername_Fail() throws Exception {
        int adminId = 1;
        String username = "nonexistentUser";
        given(adminService.searchByUsername(adminId, username)).willThrow(new UserException("User not found"));

        MvcResult result = mockMvc.perform(get("/admin/searchUser/{adminId}/{username}", adminId, username))
                .andExpect(status().isNotFound())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(), "");
    }

}
