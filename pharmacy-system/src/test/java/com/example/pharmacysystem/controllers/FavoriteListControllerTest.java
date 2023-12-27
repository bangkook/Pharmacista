package com.example.pharmacysystem.controllers;

import com.example.pharmacysystem.controller.FavoriteListController;
import com.example.pharmacysystem.dto.FavoriteItemDTO;
import com.example.pharmacysystem.model.FavoriteItem;
import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.service.FavoriteListService;
import com.example.pharmacysystem.service.ProductService;
import com.example.pharmacysystem.utils.FavoriteItemMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FavoriteListControllerTest {

    @Autowired
    FavoriteListController favoriteListController;

    @Autowired
    private FavoriteItemMapper favoriteItemMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    FavoriteListService favoriteListService;

    @MockBean
    ProductService productService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void addToFavoriteListTest_Successful() throws Exception {
        int user1 = 1;
        String productSN1 = "abc123";
        FavoriteItem favoriteItem = new FavoriteItem(user1, productSN1);

        // Mocking the repository behavior
        when(favoriteListService.add(favoriteItem)).thenReturn(true);

        // Performing the request
        MvcResult result = mockMvc.perform(post("/favorites/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(favoriteItem)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("true", content);
    }

    @Test
    public void addToFavoriteListTest_Failed() throws Exception {
        FavoriteItem favoriteItem = new FavoriteItem(1, "1");

        // Mocking the repository behavior
        when(favoriteListService.add(favoriteItem)).thenReturn(false);

        // Performing the request
        MvcResult result = mockMvc.perform(post("/favorites/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(favoriteItem)))
                .andExpect(status().isNotModified())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("false", content);
    }

    @Test
    public void deleteFromFavoriteListTest_Successful() throws Exception {
        int user1 = 1;
        String productSN1 = "abc123";
        FavoriteItem favoriteItem = new FavoriteItem(user1, productSN1);

        // Mocking the repository behavior
        when(favoriteListService.delete(favoriteItem)).thenReturn(true);

        // Performing the request
        MvcResult result = mockMvc.perform(delete("/favorites/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(favoriteItem)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("true", content);
    }

    @Test
    public void deleteFromFavoriteListTest_Failed() throws Exception {
        FavoriteItem favoriteItem = new FavoriteItem(1, "1");

        // Mocking the repository behavior
        when(favoriteListService.delete(favoriteItem)).thenReturn(false);

        // Performing the request
        MvcResult result = mockMvc.perform(delete("/favorites/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(favoriteItem)))
                .andExpect(status().isNotModified())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("false", content);
    }

    @Test
    public void findByUserIdTest_Exists() throws Exception {
        int user1 = 1;
        String productSN1 = "abc123", productSN2 = "def456";
        FavoriteItem favoriteItem1 = new FavoriteItem(user1, productSN1);
        FavoriteItem favoriteItem2 = new FavoriteItem(user1, productSN2);

        Product product1 = new Product(productSN1, 2.3F, new Date(2), new Date(2), "product", 5, "first", "url");
        Product product2 = new Product(productSN2, 2.3F, new Date(2), new Date(2), "product", 5, "second", "url");

        // Mocking the service behavior
        when(favoriteListService.findByUserId(user1)).thenReturn(Arrays.asList(favoriteItem1, favoriteItem2));
        when(productService.getProductBySerialNumber(productSN1)).thenReturn(product1);
        when(productService.getProductBySerialNumber(productSN2)).thenReturn(product2);

        // Performing the request
        MvcResult result = mockMvc.perform(get("/favorites/get/{user1}", user1))
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = result.getResponse();
        List<FavoriteItemDTO> mappedResults = Arrays.asList(objectMapper.readValue(response.getContentAsString(), FavoriteItemDTO[].class));

        // Verifying that the service method was called
        verify(favoriteListService, times(1)).findByUserId(user1);

        assertEquals(mappedResults.size(), 2);
        assertTrue(mappedResults.contains(favoriteItemMapper.toDTO(favoriteItem1, product1)));
        assertTrue(mappedResults.contains(favoriteItemMapper.toDTO(favoriteItem2, product2)));
    }

    @Test
    public void findByUserIdTest_Empty() throws Exception {
        int user1 = 1;

        // Mocking the repository behavior
        when(favoriteListService.findByUserId(user1)).thenReturn(Collections.emptyList());

        // Performing the request
        MvcResult result = mockMvc.perform(get("/favorites/get/{user1}", user1))
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = result.getResponse();
        List<FavoriteItem> mappedResults = Arrays.asList(objectMapper.readValue(response.getContentAsString(), FavoriteItem[].class));

        assertTrue(mappedResults.isEmpty());
    }

    @Test
    public void findByUserIdTest_Exception() {
        int user1 = 1;
        String productSN1 = "abc123";
        FavoriteItem favoriteItem1 = new FavoriteItem(user1, productSN1);

        // Mocking the service behavior
        when(favoriteListService.findByUserIdSorted(user1)).thenReturn(Collections.singletonList(favoriteItem1));

        // Performing the request
        // Should throw exception because product is null
        List<FavoriteItemDTO> result = favoriteListController.findByUserIdSortedAsc(user1);

        // Assert
        assertNull(result);
    }

    @Test
    public void findByUserIdSortedAscTest_Exists() throws Exception {
        int user1 = 1;
        String productSN1 = "abc123", productSN2 = "def456";
        FavoriteItem favoriteItem1 = new FavoriteItem(user1, productSN1);
        FavoriteItem favoriteItem2 = new FavoriteItem(user1, productSN2);

        Product product1 = new Product(productSN2, 2.3F, new Date(2), new Date(2), "product", 5, "first", "url");
        Product product2 = new Product(productSN1, 2.3F, new Date(2), new Date(2), "product", 5, "second", "url");

        // Mocking the service behavior
        when(favoriteListService.findByUserIdSorted(user1)).thenReturn(Arrays.asList(favoriteItem2, favoriteItem1));
        when(productService.getProductBySerialNumber(productSN1)).thenReturn(product2);
        when(productService.getProductBySerialNumber(productSN2)).thenReturn(product1);

        // Performing the request
        MvcResult result = mockMvc.perform(get("/favorites/get-sorted/{user1}", user1))
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = result.getResponse();
        List<FavoriteItemDTO> mappedResults = Arrays.asList(objectMapper.readValue(response.getContentAsString(), FavoriteItemDTO[].class));

        List<FavoriteItemDTO> expectedResult = Arrays.asList(favoriteItemMapper.toDTO(favoriteItem2, product1),
                favoriteItemMapper.toDTO(favoriteItem1, product2));

        // Assert
        assertEquals(mappedResults.size(), 2);
        assertEquals(mappedResults, expectedResult);
    }

    @Test
    public void findByUserIdSortedAscTest_Empty() throws Exception {
        int user1 = 1;

        // Mocking the repository behavior
        when(favoriteListService.findByUserIdSorted(user1)).thenReturn(Collections.emptyList());

        // Performing the request
        MvcResult result = mockMvc.perform(get("/favorites/get-sorted/{user1}", user1))
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the response
        MockHttpServletResponse response = result.getResponse();
        List<FavoriteItemDTO> mappedResults = Arrays.asList(objectMapper.readValue(response.getContentAsString(), FavoriteItemDTO[].class));

        // Assert
        assertTrue(mappedResults.isEmpty());
    }

    @Test
    public void findByUserIdSortedAscTest_WhenExceptionReturnNull() {
        int user1 = 1;
        String productSN1 = "abc123";
        FavoriteItem favoriteItem1 = new FavoriteItem(user1, productSN1);

        // Mocking the service behavior
        when(favoriteListService.findByUserIdSorted(user1)).thenReturn(Collections.singletonList(favoriteItem1));

        // Performing the request
        // Should throw exception because product is null
        List<FavoriteItemDTO> result = favoriteListController.findByUserIdSortedAsc(user1);

        // Assert
        assertNull(result);
    }

}
