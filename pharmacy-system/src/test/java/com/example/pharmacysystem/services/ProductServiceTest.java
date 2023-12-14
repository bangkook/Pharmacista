package com.example.pharmacysystem.services;


import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.repository.ProductRepository;
import com.example.pharmacysystem.service.ProductService;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Test;;
import java.util.Optional;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

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

        Assert.assertNotNull(result);
        Assert.assertEquals(expectedProduct, result);
    }

    @Test
    public void testGetProductBySerialNumber_ProductDoesNotExist() {
        String serialNumber = "XYZ456";

        when(productRepository.getProductBySerialNumber(serialNumber)).thenReturn(null);

        Product result = productService.getProductBySerialNumber(serialNumber);

        Assert.assertNull(result);
    }
    @Test
    public void testUpdateQuantityBySerialNumber_SuccessfulUpdate() {

        String productSN = "ABC123";

        int quantityToUpdate = 3;

        Product existingProduct = new Product(productSN, 2.3F, new Date(2), new Date(2), "Test", 5, "product", "url");


        when(productRepository.findById(productSN)).thenReturn(Optional.of(existingProduct));

        String result = productService.updateQuantityBySerialNumber(productSN, quantityToUpdate);

        Assert.assertEquals("Success", result);
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

        Assert.assertEquals("empty", result);
        Assert.assertEquals(5, existingProduct.getQuantity());
    }

    @Test
    public void testUpdateQuantityBySerialNumber_ProductNotFound() {

        String productSN = "XYZ456";

        when(productRepository.findById(productSN)).thenReturn(Optional.empty());

        String result = productService.updateQuantityBySerialNumber(productSN, 3);

        Assert.assertEquals("outOfStock", result);
    }


    @Test
    public void testGetAllAvailableProducts_Success() {
        List<Product> expectedProducts = Arrays.asList(
                new Product("SN123", 10.0f, null, null, "Product 1", 5, "Description 1", null),
                new Product("SN456", 15.0f, null, null, "Product 2", 8, "Description 2", null)
        );

        when(productRepository.findAllAvailableProducts()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getAllAvailableProducts();

            Assert.assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testGetAllAvailableProducts_Exception() {
        when(productRepository.findAllAvailableProducts()).thenThrow(new RuntimeException("Simulated exception"));

        RuntimeException thrownException =  Assert.assertThrows(RuntimeException.class, () -> {
            productService.getAllAvailableProducts();
        });

        Assert.assertTrue(thrownException.getMessage().contains("Error retrieving available products"));
    }



    @Test
    public void testIsAvailable_True() {
        String productSN = "SN123";
        Product availableProduct = new Product(productSN, 10.0f, null, null, "Product 1", 5, "Description 1", null);

        when(productRepository.findById(productSN)).thenReturn(Optional.of(availableProduct));

        Assert.assertTrue(productService.isAvailable(productSN));
    }

    @Test
    public void testIsAvailable_False() {
        String productSN = "SN789";

        when(productRepository.findById(productSN)).thenReturn(Optional.empty());

        Assert.assertFalse(productService.isAvailable(productSN));
    }

    @Test
    public void testIsAvailable_Exception() {
        String productSN = "SN123";

        when(productRepository.findById(productSN)).thenThrow(new RuntimeException("Simulated exception"));

        RuntimeException thrownException =  Assert.assertThrows(RuntimeException.class, () -> {
            productService.isAvailable(productSN);
        });

        Assert.assertTrue(thrownException.getMessage().contains("Error checking product availability"));
    }

    @Test
    public void testGetAllProducts() {
        // Mock data
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Test the service method
        List<Product> result = productService.getAllProducts();

        // Assertions
        Assert.assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductBySerialNumber() {

        when(productRepository.getProductBySerialNumber("123456789012345678")).thenReturn(null);

        // Test the service method
        Product result = productService.getProductBySerialNumber("123456789012345678");

        // Assertions
        Assert.assertNull(result);
        verify(productRepository, times(1)).getProductBySerialNumber("123456789012345678");
    }

    @Test
    public void testAddProduct() {
        // Mock data
        Product product = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Test the service method
        Product result = productService.addProduct(product);

        // Assertions
        Assert.assertNotNull(result);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testUpdateProduct() {
        // Mock data
        Product existingProduct = new Product();
        when(productRepository.getProductBySerialNumber("123456789012345678")).thenReturn(existingProduct);
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Product updatedProduct = new Product();

        // Test the service method
        Product result = productService.updateProduct("123456789012345678", updatedProduct);

        // Assertions
        Assert.assertNotNull(result);
        verify(productRepository, times(1)).getProductBySerialNumber("123456789012345678");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testDeleteProduct() {
        // Mock data
        doNothing().when(productRepository).deleteById("123456789012345678");

        // Test the service method
        assertDoesNotThrow(() -> productService.deleteProduct("123456789012345678"));

        // Assertions
        verify(productRepository, times(1)).deleteById("123456789012345678");

    }

}
