package com.example.pharmacysystem.controllers;

import com.example.pharmacysystem.controller.listOfProductsController;
import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class listOfProductsControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private listOfProductsController listOfProductsController;

    @Autowired
    private WebApplicationContext context;


    @Test
    public void getAllAvailableProducts_Success() throws Exception {
        Product product1 = new Product("1", 20.0f, Date.valueOf("2023-01-01"), Date.valueOf("2023-12-30"), "Description 1", 10, "Product 1", "photo1");
        Product product2 = new Product("2", 25.0f, Date.valueOf("2023-02-01"), Date.valueOf("2023-12-31"), "Description 2", 15, "Product 2", "photo2");
        List<Product> mockProducts = Arrays.asList(product1, product2);

        when(productService.getAllAvailableProducts()).thenReturn(mockProducts);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/product/getAllAvailableProducts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"serialNumber\":\"1\",\"price\":20.0,\"productionDate\":\"2023-01-01\",\"expiryDate\":\"2023-12-30\",\"description\":\"Description 1\",\"quantity\":10,\"name\":\"Product 1\",\"photo\":\"photo1\"},{\"serialNumber\":\"2\",\"price\":25.0,\"productionDate\":\"2023-02-01\",\"expiryDate\":\"2023-12-31\",\"description\":\"Description 2\",\"quantity\":15,\"name\":\"Product 2\",\"photo\":\"photo2\"}]"));
    }

    @Test
    public void getAllAvailableProducts_Failure() throws Exception {

        when(productService.getAllAvailableProducts()).thenThrow(new RuntimeException("Simulated exception"));

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/getAllAvailableProducts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        String expectedResponse = "";  // You can set an expected error message
        String resultContent = result.getResponse().getContentAsString();
        Assert.assertEquals(expectedResponse, resultContent);
    }

    @Test
    public void testIsAvailableProducts_True() throws Exception {
        String productSN = "SN123";
        boolean expectedAvailability = true;

        // Mock the productService.isAvailable method
        when(productService.isAvailable(productSN)).thenReturn(expectedAvailability);

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        // Perform the HTTP request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/product/isAvailableProducts/{SN}", productSN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Extract the JSON response
        String jsonResponse = result.getResponse().getContentAsString();

        // Parse the JSON response back to a boolean
        boolean actualAvailability = Boolean.parseBoolean(jsonResponse);

        // Assert that the returned boolean is equal to expectedAvailability
        Assert.assertEquals(expectedAvailability, actualAvailability);
    }

    @Test
    public void testIsAvailableProducts_Exception() throws Exception {
        String productSN = "SN456";

        // Mock the productService.isAvailable method to throw an exception
        when(productService.isAvailable(productSN)).thenThrow(new RuntimeException("Simulated exception"));

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Perform the HTTP request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/product/isAvailableProducts/{SN}", productSN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        // Assert the HTTP status is INTERNAL_SERVER_ERROR (500)
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }



}
