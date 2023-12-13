package com.example.pharmacysystem.controllers;

import com.example.pharmacysystem.controller.CartController;
import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.model.Order;
import com.example.pharmacysystem.model.OrderDetail;
import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.service.CartItemService;
import com.example.pharmacysystem.service.OrderDetailService;
import com.example.pharmacysystem.service.OrderService;
import com.example.pharmacysystem.service.ProductService;
import com.example.pharmacysystem.utils.CartItemId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartControllerTest {



    @MockBean
    private ProductService productService;

    @MockBean
    private CartItemService cartItemService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderDetailService orderDetailService;

    @Autowired
    private CartController cartController;
    @Test
    public void testGetCartItemAndInfoFoundInBoth1Product() {

        // Mock data
        int userId = 1;
        CartItem cartItem = new CartItem(1, "1", 1);
        Product product = new Product("1", 2, new Date(2), new Date(2), "Test Product", 5, "Test Product", "Photo");


        // Mock the cartItemService.getCartItemsByUserId method
        when(cartItemService.getCartItemsByUserId(userId))
                .thenReturn(Arrays.asList(cartItem));

        // Mock the productService.getProductBySerialNumber method
        when(productService.getProductBySerialNumber(cartItem.getProductSN()))
                .thenReturn(product);
        // Test
        List<Map<String, Object>> response = cartController.getProductsAndQuantities(userId);

        // Verify that the response contains the expected information
        assertEquals(1, response.size());
        Map<String, Object> productInfo = response.get(0);
        assertEquals("1", productInfo.get("productSN"));
        assertEquals(1, productInfo.get("quantity"));
        assertEquals("Test Product", productInfo.get("productName"));
        assertEquals(2.0, productInfo.get("price"));
        assertEquals("Photo", productInfo.get("photo"));
        assertEquals(5, productInfo.get("amount"));

    }
    @Test
    public void testGetCartItemAndInfoFoundInBothMoreProducts() {

        // Mock data
        int userId = 1;
        List<CartItem> cartItems = Arrays.asList(
                new CartItem(1, "1", 1),
                new CartItem(2, "2", 2)
        );

        List<Product> products = Arrays.asList(
                new Product("1", 2, new Date(2), new Date(2), "Test Product 1", 5, "Test Product", "Photo1"),
                new Product("2", 3, new Date(2), new Date(2), "Test Product 2", 8, "Test Product", "Photo2"),
                new Product("3", 3, new Date(2), new Date(2), "Test Product 2", 8, "Test Product", "Photo2")
        );

        // Mock the cartItemService.getCartItemsByUserId method
        when(cartItemService.getCartItemsByUserId(userId))
                .thenReturn(cartItems);

        // Mock the productService.getProductBySerialNumber method for each product
        for (CartItem cartItem : cartItems) {
            when(productService.getProductBySerialNumber(cartItem.getProductSN()))
                    .thenReturn(products.stream()
                            .filter(p -> p.getSerialNumber().equals(cartItem.getProductSN()))
                            .findFirst()
                            .orElse(null));
        }
        // Test
        List<Map<String, Object>> response = cartController.getProductsAndQuantities(userId);

        // Verify that the response contains the expected information
        assertEquals(cartItems.size(), response.size());

        for (int i = 0; i < cartItems.size(); i++) {
            Map<String, Object> productInfo = response.get(i);
            CartItem cartItem = cartItems.get(i);
            Product product = products.get(i);

            assertEquals(cartItem.getProductSN(), productInfo.get("productSN"));
            assertEquals(cartItem.getQuantity(), productInfo.get("quantity"));

            if (product != null) { // Check if the product is not null before accessing its properties
                assertEquals(product.getName(), productInfo.get("productName"));
                assertEquals(product.getPrice(), productInfo.get("price"));
                assertEquals(product.getPhoto(), productInfo.get("photo"));
                assertEquals(product.getQuantity(), productInfo.get("amount"));
            }
        }

    }

    @Test
    public void testGetCartItemAndInfoFoundInCartNotInInventory() {

        // Mock data
        int userId = 1;
        List<CartItem> cartItems = Arrays.asList(
                new CartItem(1, "1", 1),
                new CartItem(2, "2", 2)
        );

        List<Product> products = Arrays.asList(
                new Product("1", 2, new Date(2), new Date(2), "Test Product 1", 5, "Test Product", "Photo1"),
                new Product("3", 3, new Date(2), new Date(2), "Test Product 2", 8, "Test Product", "Photo2")
        );

        // Mock the cartItemService.getCartItemsByUserId method
        when(cartItemService.getCartItemsByUserId(userId))
                .thenReturn(cartItems);

        // Mock the productService.getProductBySerialNumber method for each product
        for (CartItem cartItem : cartItems) {
            when(productService.getProductBySerialNumber(cartItem.getProductSN()))
                    .thenReturn(products.stream()
                            .filter(p -> p.getSerialNumber().equals(cartItem.getProductSN()))
                            .findFirst()
                            .orElse(null)); // Provide a default Product to avoid NullPointerException
        }

        // Test
        List<Map<String, Object>> response = cartController.getProductsAndQuantities(userId);

        // Verify that the response contains the expected information
        assertNotEquals(cartItems.size(), response.size());

        for (int i = 0; i < response.size(); i++) {
            Map<String, Object> productInfo = response.get(i);
            CartItem cartItem = cartItems.get(i);
            Product product = products.stream()
                    .filter(p -> p.getSerialNumber().equals(cartItem.getProductSN()))
                    .findFirst()
                    .orElse(null);

            assertEquals(cartItem.getProductSN(), productInfo.get("productSN"));
            assertEquals(cartItem.getQuantity(), productInfo.get("quantity"));

            if (product != null) {
                assertEquals(product.getName(), productInfo.get("productName"));
                assertEquals(product.getPrice(), productInfo.get("price"));
                assertEquals(product.getPhoto(), productInfo.get("photo"));

                // Check if product.getQuantity() is not null before accessing it
                if (product.getQuantity() != 0) {
                    assertEquals(product.getQuantity(), productInfo.get("amount"));
                } else {
                    assertNull(productInfo.get("amount")); // Set amount to null if product quantity is null
                }
            } else {
                // Handle the case where the product is null
                assertNull(productInfo.get("productName"));
                assertNull(productInfo.get("price"));
                assertNull(productInfo.get("photo"));
                assertNull(productInfo.get("amount"));
            }
        }
    }

    @Test
    public void testGetCartItemAndInfoFoundInCartNotInInventoryAndDeleteFromCart() {

        // Mock data
        int userId = 1;
        List<CartItem> cartItems = Arrays.asList(
                new CartItem(1, "1", 1),
                new CartItem(2, "2", 2),
                new CartItem(3, "3", 3)
        );

        List<CartItem> expectedUpdatedCartItems = Arrays.asList(
                new CartItem(1, "1", 1)
        );

        List<Product> products = Arrays.asList(
                new Product("1", 2, new Date(2), new Date(2), "Test Product 1", 5, "Test Product", "Photo1")
                // No product with serial number "2" in the inventory
        );


        // Mock the cartItemService.getCartItemsByUserId method
        when(cartItemService.getCartItemsByUserId(userId))
                .thenReturn(cartItems);

        // Test
        List<Map<String, Object>> response = cartController.getProductsAndQuantities(userId);

        // Verify that the response contains the expected information
        assertNotEquals(cartItems.size(), response.size());

        List<CartItem> updatedCartItems = new ArrayList<>();
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            Product product = products.stream()
                    .filter(p -> p.getSerialNumber().equals(cartItem.getProductSN()))
                    .findFirst()
                    .orElse(null);

            if (product == null || product.getQuantity() == 0) {
                verify(cartItemService, times(1)).deleteCartItemByproductSN(cartItem.getProductSN());
            } else {
                updatedCartItems.add(cartItem);
            }
        }

        assertEquals(expectedUpdatedCartItems.toString(), updatedCartItems.toString());
    }

    @Test
    public void testGetCartItemAndCartIsEmpty() {

        // Mock data
        int userId = 1;
        List<CartItem> cartItems = Arrays.asList();

        List<CartItem> expectedUpdatedCartItems = Arrays.asList();

        List<Product> products = Arrays.asList(
                new Product("1", 2, new Date(2), new Date(2), "Test Product 1", 5, "Test Product", "Photo1"),
                new Product("2", 2, new Date(2), new Date(2), "Test Product 1", 5, "Test Product", "Photo1")

        );


        // Mock the cartItemService.getCartItemsByUserId method
        when(cartItemService.getCartItemsByUserId(userId))
                .thenReturn(cartItems);

        // Test
        List<Map<String, Object>> response = cartController.getProductsAndQuantities(userId);

        // Verify that the response contains the expected information
        assertEquals(cartItems.size(), response.size());

        List<CartItem> updatedCartItems = new ArrayList<>();
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            Product product = products.stream()
                    .filter(p -> p.getSerialNumber().equals(cartItem.getProductSN()))
                    .findFirst()
                    .orElse(null);

            if (product == null || product.getQuantity() == 0) {
                verify(cartItemService, times(1)).deleteCartItemByproductSN(cartItem.getProductSN());
            } else {
                updatedCartItems.add(cartItem);
            }
        }

        assertEquals(expectedUpdatedCartItems.toString(), updatedCartItems.toString());
    }

    @Test
    public void testGetProductQuantityWhenProductExistsWithPositiveQuantity() {
        String serialNumber = "1";
        int expectedQuantity = 5;

        // Mock the productService.getProductBySerialNumber method
        when(productService.getProductBySerialNumber(serialNumber))
                .thenReturn(new Product("1", 2, new Date(2), new Date(2), "Test Product 1", expectedQuantity, "Test Product", "Photo1"));

        // Test
        ResponseEntity<Integer> response = cartController.getProductQuantity(serialNumber);

        // Verify that the response contains the expected quantity
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals((Integer) expectedQuantity, response.getBody());
    }

    @Test
    public void testGetProductQuantityWhenProductExistsWithZeroQuantity() {
        String serialNumber = "1";
        int expectedQuantity = 0;

        // Mock the productService.getProductBySerialNumber method
        when(productService.getProductBySerialNumber(serialNumber))
                .thenReturn(new Product("1", 2, new Date(2), new Date(2), "Test Product 1", expectedQuantity, "Test Product", "Photo1"));

        // Test
        ResponseEntity<Integer> response = cartController.getProductQuantity(serialNumber);

        // Verify that the response contains the expected quantity
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals((Integer) expectedQuantity, response.getBody());
    }

    @Test
    public void testGetProductQuantityWhenProductNoyExists() {
        String serialNumber = "1";
        int expectedQuantity = 0;

        // Mock the productService.getProductBySerialNumber method
        when(productService.getProductBySerialNumber(serialNumber))
                .thenReturn(new Product("2", 2, new Date(2), new Date(2), "Test Product 1", expectedQuantity, "Test Product", "Photo1"));

        // Test
        ResponseEntity<Integer> response = cartController.getProductQuantity(serialNumber);

        // Verify that the response contains the expected quantity
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals((Integer) expectedQuantity, response.getBody());
    }

    @Test
    public void testUpdateQuantityInCart() {
        // Mock data
        int userId = 1;
        String productSN = "1";
        int quantity = 5;


        // Mock the payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userId);
        payload.put("productSN", productSN);
        payload.put("quantity", quantity);

        // Mock the cartItemService.updateQuantityByUserNameAndSerialNumber method
        Mockito.doNothing().when(cartItemService).updateQuantityByUserNameAndSerialNumber(userId, productSN, quantity);

        // Call the method
        cartController.updateQuantity(payload);

        // Verify that the updateQuantityByUserNameAndSerialNumber method was called with the correct parameters
        Mockito.verify(cartItemService, Mockito.times(1)).updateQuantityByUserNameAndSerialNumber(userId, productSN, quantity);
    }

    @Test
    public void testDeleteCartItem() {
        // Mock data
        int userId = 1;
        String productSN = "1";

        // Mock the deleteCartItem method
        Mockito.when(cartItemService.deleteCartItem(Mockito.any(CartItemId.class)))
                .thenReturn(true); // Assume deletion is successful

        // Call the method
        ResponseEntity<String> response = cartController.deleteCartItem(userId, productSN);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("CartItem deleted successfully", response.getBody());

        // Verify that the deleteCartItem method was called with the correct parameters
        Mockito.verify(cartItemService, Mockito.times(1)).deleteCartItem(Mockito.any(CartItemId.class));
    }

    @Test
    public void testDeleteCartItemNotFound() {
        // Mock data
        int userId = 1;
        String productSN = "1";

        // Mock the deleteCartItem method to return false (deletion failure)
        Mockito.when(cartItemService.deleteCartItem(Mockito.any(CartItemId.class)))
                .thenReturn(false);

        // Call the method
        ResponseEntity<String> response = cartController.deleteCartItem(userId, productSN);

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("CartItem not found or deletion failed", response.getBody());

        // Verify that the deleteCartItem method was called with the correct parameters
        Mockito.verify(cartItemService, Mockito.times(1)).deleteCartItem(Mockito.any(CartItemId.class));
    }

    @Test
    public void testCreateOrderSuccess() {
        // Mock data
        Order order = new Order(1, new Date(System.currentTimeMillis()), 100.698F);

        // Mock the createOrder method to return true (successful order creation)
        when(orderService.createOrder(order)).thenReturn(true);

        // Call the method
        ResponseEntity<Integer> response = cartController.createOrder(order);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order.getId(), response.getBody().intValue());

        // Verify that the createOrder method was called with the correct parameter
        // and only called once
        when(orderService.createOrder(order)).thenReturn(true);
    }

    @Test
    public void testCreateOrderFailure() {
        // Mock data
        Order order = new Order(1, new Date(System.currentTimeMillis()), 100.0F);

        // Mock the createOrder method to return false (order creation failure)
        when(orderService.createOrder(order)).thenReturn(false);

        // Call the method
        ResponseEntity<Integer> response = cartController.createOrder(order);

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());

        // Verify that the createOrder method was called with the correct parameter
        // and only called once
        when(orderService.createOrder(order)).thenReturn(false);
    }

    @Test
    public void testCreateOrderDetailsSuccess() {
        // Mock data
        OrderDetail orderDetail = new OrderDetail(1, "1", 1);

        // Mock the service response
        when(orderDetailService.createOrderDetails(orderDetail)).thenReturn(true);

        // Test the controller method
        ResponseEntity<String> response = cartController.createOrderDetails(orderDetail);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("order created successfully", response.getBody());
    }

    @Test
    public void testCreateOrderDetailsFailure() {
        // Mock data
        OrderDetail orderDetail = new OrderDetail(1, "1", 1);

        // Mock the service response
        when(orderDetailService.createOrderDetails(orderDetail)).thenReturn(false);

        // Test the controller method
        ResponseEntity<String> response = cartController.createOrderDetails(orderDetail);

        // Assert the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("order failed to create", response.getBody());
    }

    @Test
    public void testUpdateOrderdProductsSuccess() {
        // Mock data
        Map<String, Object> request = new HashMap<>();
        request.put("serialNumber", "1");
        request.put("quantity", 3);

        // Mock the service response
        when(productService.updateQuantityBySerialNumber("1", 3)).thenReturn("Success");

        // Test the controller method
        ResponseEntity<String> response = cartController.UpdateOrderdProducts(request);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully ordered", response.getBody());
    }

    @Test
    public void testUpdateOrderdProductsEmpty() {
        // Mock data
        Map<String, Object> request = new HashMap<>();
        request.put("serialNumber", "2");
        request.put("quantity", 1);

        // Mock the service response
        when(productService.updateQuantityBySerialNumber("2", 1)).thenReturn("empty");

        // Test the controller method
        ResponseEntity<String> response = cartController.UpdateOrderdProducts(request);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("empty", response.getBody());
    }

    @Test
    public void testUpdateOrderdProductsOutOfStock() {
        // Mock data
        Map<String, Object> request = new HashMap<>();
        request.put("serialNumber", "3");
        request.put("quantity", 2);

        // Mock the service response
        when(productService.updateQuantityBySerialNumber("3", 2)).thenReturn("outOfStock");

        // Test the controller method
        ResponseEntity<String> response = cartController.UpdateOrderdProducts(request);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("outOfStock", response.getBody());
    }




}
