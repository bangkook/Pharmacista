package com.example.pharmacysystem.services;


import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.repository.ProductRepository;
import com.example.pharmacysystem.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.repository.ProductRepository;
import com.example.pharmacysystem.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testGetAllAvailableProducts_Success() {
        List<Product> expectedProducts = Arrays.asList(
                new Product("SN123", 10.0f, null, null, "Product 1", 5, "Description 1", null),
                new Product("SN456", 15.0f, null, null, "Product 2", 8, "Description 2", null)
        );

        when(productRepository.findAllAvailableProducts()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getAllAvailableProducts();

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testGetAllAvailableProducts_Exception() {
        when(productRepository.findAllAvailableProducts()).thenThrow(new RuntimeException("Simulated exception"));

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            productService.getAllAvailableProducts();
        });

        assertTrue(thrownException.getMessage().contains("Error retrieving available products"));
    }



    @Test
    public void testIsAvailable_True() {
        String productSN = "SN123";
        Product availableProduct = new Product(productSN, 10.0f, null, null, "Product 1", 5, "Description 1", null);

        when(productRepository.findById(productSN)).thenReturn(Optional.of(availableProduct));

        assertTrue(productService.isAvailable(productSN));
    }

    @Test
    public void testIsAvailable_False() {
        String productSN = "SN789";

        when(productRepository.findById(productSN)).thenReturn(Optional.empty());

        assertFalse(productService.isAvailable(productSN));
    }

    @Test
    public void testIsAvailable_Exception() {
        String productSN = "SN123";

        when(productRepository.findById(productSN)).thenThrow(new RuntimeException("Simulated exception"));

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            productService.isAvailable(productSN);
        });

        assertTrue(thrownException.getMessage().contains("Error checking product availability"));
    }

    @Test
    void testGetAllProducts() {
        // Mock data
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Test the service method
        List<Product> result = productService.getAllProducts();

        // Assertions
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductBySerialNumber() {
        // Mock data
        Product product = new Product();
        when(productRepository.findBySerialNumber("123456789012345678")).thenReturn(Optional.of(product));

        // Test the service method
        Optional<Product> result = productService.getProductBySerialNumber("123456789012345678");

        // Assertions
        assertTrue(result.isPresent());
        verify(productRepository, times(1)).findBySerialNumber("123456789012345678");
    }

    @Test
    void testAddProduct() {
        // Mock data
        Product product = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Test the service method
        Product result = productService.addProduct(product);

        // Assertions
        assertNotNull(result);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct() {
        // Mock data
        Product existingProduct = new Product();
        when(productRepository.findBySerialNumber("123456789012345678")).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Product updatedProduct = new Product();

        // Test the service method
        Product result = productService.updateProduct("123456789012345678", updatedProduct);

        // Assertions
        assertNotNull(result);
        verify(productRepository, times(1)).findBySerialNumber("123456789012345678");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        // Mock data
        doNothing().when(productRepository).deleteById("123456789012345678");

        // Test the service method
        assertDoesNotThrow(() -> productService.deleteProduct("123456789012345678"));

        // Assertions
        verify(productRepository, times(1)).deleteById("123456789012345678");
    }

}
