package com.example.pharmacysystem.services;

import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.repository.ProductRepository;
import com.example.pharmacysystem.service.ProductService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;;
import java.util.Optional;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void testGetProductBySerialNumber_ProductExists() {

        String serialNumber = "ABC123";
        Product expectedProduct = new Product(serialNumber, 2.3F, new Date(2), new Date(2), "Test", 1, "product", "url");

        when(productRepository.getProductBySerialNumber(serialNumber)).thenReturn(expectedProduct);

        Product result = productService.getProductBySerialNumber(serialNumber);

        assertNotNull(result);
        assertEquals(expectedProduct, result);
    }

    @Test
    public void testGetProductBySerialNumber_ProductDoesNotExist() {
        String serialNumber = "XYZ456";

        when(productRepository.getProductBySerialNumber(serialNumber)).thenReturn(null);

        Product result = productService.getProductBySerialNumber(serialNumber);

        assertNull(result);
    }
    @Test
    public void testUpdateQuantityBySerialNumber_SuccessfulUpdate() {

        String productSN = "ABC123";

        int quantityToUpdate = 3;

        Product existingProduct = new Product(productSN, 2.3F, new Date(2), new Date(2), "Test", 5, "product", "url");


        when(productRepository.findById(productSN)).thenReturn(Optional.of(existingProduct));

        String result = productService.updateQuantityBySerialNumber(productSN, quantityToUpdate);

        assertEquals("Success", result);
        assertEquals(2, existingProduct.getQuantity());
    }

    @Test
    public void testUpdateQuantityBySerialNumber_InsufficientStock() {
        // Arrange
        String productSN = "ABC123";

        int quantityToUpdate = 8;

        Product existingProduct = new Product(productSN, 2.3F, new Date(2), new Date(2), "Test", 5, "product", "url");

        when(productRepository.findById(productSN)).thenReturn(Optional.of(existingProduct));

        String result = productService.updateQuantityBySerialNumber(productSN, quantityToUpdate);

        assertEquals("empty", result);
        assertEquals(5, existingProduct.getQuantity());
    }

    @Test
    public void testUpdateQuantityBySerialNumber_ProductNotFound() {

        String productSN = "XYZ456";

        when(productRepository.findById(productSN)).thenReturn(Optional.empty());

        String result = productService.updateQuantityBySerialNumber(productSN, 3);

        assertEquals("outOfStock", result);
    }

}
