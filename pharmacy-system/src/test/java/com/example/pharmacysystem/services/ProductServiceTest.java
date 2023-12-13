package com.example.pharmacysystem.services;

import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.repository.ProductRepository;
import com.example.pharmacysystem.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

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
