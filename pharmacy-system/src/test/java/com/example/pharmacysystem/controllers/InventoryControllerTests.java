package com.example.pharmacysystem.controllers;

import com.example.pharmacysystem.controller.InventoryController;
import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class InventoryControllerTests {

    @MockBean
    private ProductService productService;

    @Autowired
    private InventoryController inventoryController;

    @Autowired
    private WebApplicationContext context;

    @Test
    void testGetAllProducts() throws Exception {
        // Mock the productService.getAllProducts method
        when(productService.getAllProducts()).thenReturn(List.of(
                new Product(
                        "123456789012345678",
                        19.99F,
                        Date.valueOf(LocalDate.now()),
                        Date.valueOf(LocalDate.now().plusMonths(1)),
                        "Product1",
                        10,
                        "Product1",
                        "product1.jpg"
                )
        ));

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Perform the GET request
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Product1"));
    }

    @Test
    void testAddProductValid() throws Exception {
        // Mock the productService.getProductBySerialNumber method
        when(productService.getProductBySerialNumber(anyString())).thenReturn(Optional.empty());

        // Mock the productService.addProduct method
        when(productService.addProduct(any())).thenReturn(
                new Product(
                        "123456789012345678",
                        29.99F,
                        Date.valueOf(LocalDate.now()),
                        Date.valueOf(LocalDate.now().plusMonths(2)),
                        "NewProduct",
                        5,
                        "NewProduct",
                        "new_product.jpg"
                )
        );

        // Mock the payload data
        Product payload = new Product(
                "123456789012345678",
                29.99F,
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusMonths(2)),
                "NewProduct",
                5,
                "NewProduct",
                "new_product.jpg"
        );

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Perform the POST request
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(payload)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("NewProduct"));
    }

    @Test
    void testAddProductInvalidSerialNumber() throws Exception {
        // Mock the productService.getProductBySerialNumber method to return an empty optional
        when(productService.getProductBySerialNumber(anyString())).thenReturn(Optional.empty());

        // Mock the payload data with an invalid serial number (less than 18 characters)
        Product payload = new Product(
                "12345678901234567",  // Serial number with less than 18 characters
                29.99F,
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusMonths(2)),
                "InvalidProduct",
                5,
                "InvalidProduct",
                "invalid_product.jpg"
        );

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Perform the POST request
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(payload)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddProductDuplicateSerialNumber() throws Exception {
        // Mock the productService.getProductBySerialNumber method to return a product
        when(productService.getProductBySerialNumber(anyString())).thenReturn(
                Optional.of(new Product(
                        "123456789012345678",
                        19.99F,
                        Date.valueOf(LocalDate.now()),
                        Date.valueOf(LocalDate.now().plusMonths(1)),
                        "ExistingProduct",
                        3,
                        "ExistingProduct",
                        "existing_product.jpg"
                ))
        );

        // Mock the payload data
        Product payload = new Product(
                "123456789012345678",
                29.99F,
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusMonths(2)),
                "NewProduct",
                5,
                "NewProduct",
                "new_product.jpg"
        );

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Perform the POST request
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(payload)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Serial number must be unique."));
    }

    @Test
    void testUpdateProductValid() throws Exception {
        // Mock the productService.updateProduct method
        when(productService.updateProduct(anyString(), any())).thenReturn(
                new Product(
                        "123456789012345678",
                        29.99F,
                        Date.valueOf(LocalDate.now()),
                        Date.valueOf(LocalDate.now().plusMonths(2)),
                        "UpdatedProduct",
                        5,
                        "UpdatedProduct",
                        "updated_product.jpg"
                )
        );

        // Mock the payload data
        Product payload = new Product(
                "123456789012345678",
                29.99F,
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusMonths(2)),
                "UpdatedProduct",
                5,
                "UpdatedProduct",
                "updated_product.jpg"
        );

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Perform the PUT request
        mockMvc.perform(put("/products/{serialNumber}", "123456789012345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(payload)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("UpdatedProduct"));
    }

    @Test
    void testUpdateProductInvalidSerialNumber() throws Exception {
        when(productService.updateProduct(anyString(), any())).thenReturn(null);

        // Mock the payload data with invalid fields
        Product payload = new Product(
                "1234567890123",
                10.0F, // Invalid price
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusMonths(2)),
                "UpdatedProduct",
                10,
                "UpdatedProduct",
                "updated_product.jpg"
        );

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Perform the PUT request
        mockMvc.perform(put("/products/{serialNumber}", "123456789012345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(payload)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateProductInvalidPrice() throws Exception {
        when(productService.updateProduct(anyString(), any())).thenReturn(null);

        // Mock the payload data with invalid fields
        Product payload = new Product(
                "123456789012345678",
                -10.0F, // Invalid price
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusMonths(2)),
                "UpdatedProduct",
                10,
                "UpdatedProduct",
                "updated_product.jpg"
        );

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Perform the PUT request
        mockMvc.perform(put("/products/{serialNumber}", "123456789012345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(payload)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateProductInvalidQuantity() throws Exception {
        when(productService.updateProduct(anyString(), any())).thenReturn(null);

        // Mock the payload data with invalid fields
        Product payload = new Product(
                "123456789012345678",
                10.0F,
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusMonths(2)),
                "UpdatedProduct",
                -5, // Invalid quantity
                "UpdatedProduct",
                "updated_product.jpg"
        );

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Perform the PUT request
        mockMvc.perform(put("/products/{serialNumber}", "123456789012345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(payload)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateProductInvalidDate() throws Exception {
        when(productService.updateProduct(anyString(), any())).thenReturn(null);

        // Mock the payload data with invalid fields
        Product payload = new Product(
                "123456789012345678",
                10.0F,
                Date.valueOf(LocalDate.now().plusMonths(1)),
                Date.valueOf(LocalDate.now().plusMonths(2)),
                "UpdatedProduct",
                5, // Invalid quantity
                "UpdatedProduct",
                "updated_product.jpg"
        );

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Perform the PUT request
        mockMvc.perform(put("/products/{serialNumber}", "123456789012345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(payload)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteProduct() throws Exception {
        // Mock the productService.deleteProduct method
        doNothing().when(productService).deleteProduct(anyString());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Perform the DELETE request
        mockMvc.perform(delete("/products/{serialNumber}", "123456789012345678"))
                .andExpect(status().isNoContent());

        // Verify that productService.deleteProduct was called with the correct serial number
        verify(productService, times(1)).deleteProduct("123456789012345678");
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
