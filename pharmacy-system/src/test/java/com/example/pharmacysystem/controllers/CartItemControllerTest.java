package com.example.pharmacysystem.controllers;

import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.controller.CartItemController;
import com.example.pharmacysystem.service.CartItemService;
import com.example.pharmacysystem.utils.CartItemId;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartItemControllerTest {

    @MockBean
    private CartItemService cartItemService;

    @Autowired
    private CartItemController cartItemController;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void addItem_CorrectItem() throws Exception {
        CartItem newItem = new CartItem(1, "SN112", 1);

        when(cartItemService.saveCartItem(any(CartItem.class))).thenReturn(true);


        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        MvcResult res = mockMvc.perform(post("/cartItem/addCartItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem)))
                .andReturn();

        String resultContent = res.getResponse().getContentAsString();
        System.out.println("Result Content: " + resultContent);

        String expectedResponse = "Item added successfully";
        Assert.assertEquals(expectedResponse, resultContent);
    }

    @Test
    public void addItem_IncorrectItem() throws Exception {
        CartItem newItem = new CartItem(1, "SN112", 1);

        when(cartItemService.saveCartItem(any(CartItem.class))).thenReturn(false);


        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        MvcResult res = mockMvc.perform(post("/cartItem/addCartItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem)))
                .andReturn();

        String resultContent = res.getResponse().getContentAsString();
        System.out.println("Result Content: " + resultContent);

        String expectedResponse = "Failed to add item";
        Assert.assertEquals(expectedResponse, resultContent);
    }

    @Test
    public void deleteCartItem_Success() throws Exception {
        int userId = 1;
        String productSN = "SN1";

        when(cartItemService.deleteCartItem(new CartItemId(userId, productSN))).thenReturn(true);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cartItem/removeCartItem/{userId}/{productSN}", userId, productSN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expectedResponse = "CartItem deleted successfully";
        Assert.assertEquals(expectedResponse, result.getResponse().getContentAsString());
        Assert.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void deleteCartItem_NotFound() throws Exception {
        int userId = 1;
        String productSN = "SN456";

        when(cartItemService.deleteCartItem(any(CartItemId.class))).thenReturn(false);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cartItem/removeCartItem/{userId}/{productSN}", userId, productSN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expectedResponse = "CartItem not found or deletion failed";
        Assert.assertEquals(expectedResponse, result.getResponse().getContentAsString());
        Assert.assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    public void getCartItemsByUser_Success() throws Exception {
        int userId = 1;
        List<String> mockProductSNs = Arrays.asList("SN1", "SN2");

        when(cartItemService.getCartItemsByUserId(userId)).thenReturn(Arrays.asList(
                new CartItem(userId, "SN1", 1),
                new CartItem(userId, "SN2", 2)
        ));

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/cartItem/ProductFromCart/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        List<String> actualProductSNs = Arrays.asList(jsonResponse.replaceAll("[\\[\\]\"]", "").split(","));

        Assert.assertEquals(mockProductSNs, actualProductSNs);
    }

    @Test
    public void getCartItemsByUser_Failure() throws Exception {
        int userId = 1;

        when(cartItemService.getCartItemsByUserId(userId)).thenThrow(new RuntimeException("Simulated exception"));

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/cartItem/ProductFromCart/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String expectedResponse = "";
        String resultContent = result.getResponse().getContentAsString();
        Assert.assertEquals(expectedResponse, resultContent);
    }

}
