package com.example.pharmacysystem.services;

import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.repository.CartItemRepository;
import com.example.pharmacysystem.service.CartItemService;
import com.example.pharmacysystem.service.CartItemServiceImpl;
import com.example.pharmacysystem.utils.CartItemId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTest {

    @MockBean
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemService cartItemService;

    @Test
    public void testGetCartItemsByUserId() {
        // Mock data
        int userId = 1;
        List<CartItem> expectedCartItems = Arrays.asList(
                new CartItem(1, "1", 2)
        );

        // Mock the repository method
        when(cartItemRepository.getCartItemsByUserId(userId)).thenReturn(expectedCartItems);

        // Test the service method
        List<CartItem> actualCartItems = cartItemService.getCartItemsByUserId(userId);

        // Verify that the repository method was called
        verify(cartItemRepository, times(1)).getCartItemsByUserId(userId);

        // Assert the result
        assertNotNull(actualCartItems);
        assertEquals(expectedCartItems.size(), actualCartItems.size());
        assertEquals(expectedCartItems, actualCartItems);
    }

    @Test
    public void testGetCartItemsByUserIdNotFound() {
        // Mock data
        int userId = 1;
        List<CartItem> expectedCartItems = Collections.emptyList(); // Empty list to simulate no items found

        // Mock the repository method
        when(cartItemRepository.getCartItemsByUserId(userId)).thenReturn(expectedCartItems);

        // Test the service method
        List<CartItem> actualCartItems = cartItemService.getCartItemsByUserId(userId);

        // Verify that the repository method was called
        verify(cartItemRepository, times(1)).getCartItemsByUserId(userId);

        // Assert the result
        assertNotNull(actualCartItems);
        assertTrue(actualCartItems.isEmpty());
    }


    @Test
    public void testUpdateQuantityByUserNameAndSerialNumber() {
        // Mock data
        int userId = 1;
        String productSN = "1";
        int quantity = 5;

        CartItem existingCartItem = new CartItem(userId, productSN, 2);

        // Mock the repository method
        when(cartItemRepository.findByUserNameAndSerialNumber(userId, productSN))
                .thenReturn(existingCartItem);

        // Test the service method
        cartItemService.updateQuantityByUserNameAndSerialNumber(userId, productSN, quantity);

        // Verify that the repository method was called
        verify(cartItemRepository, times(1)).findByUserNameAndSerialNumber(userId, productSN);

        // Verify that the cart item was updated
        assertEquals(quantity, existingCartItem.getQuantity());
        verify(cartItemRepository, times(1)).save(existingCartItem);
    }

    @Test
    public void testDeleteCartItem() {
        // Mock data
        int userId = 1;
        String productSN = "1";
        CartItemId cartItemId = new CartItemId(userId, productSN);

        // Mock the repository method
        doNothing().when(cartItemRepository).deleteById(cartItemId);

        // Test the service method
        boolean result = cartItemService.deleteCartItem(cartItemId);

        // Verify that the repository method was called
        verify(cartItemRepository, times(1)).deleteById(cartItemId);

        // Assert the result
        assertTrue(result);
    }

    @Test
    public void testDeleteCartItemFailure() {
        // Mock data
        int userId = 1;
        String productSN = "1";
        CartItemId cartItemId = new CartItemId(userId, productSN);

        // Mock the repository method to throw an exception
        doThrow(new RuntimeException("Deletion failed")).when(cartItemRepository).deleteById(cartItemId);

        // Test the service method
        boolean result = cartItemService.deleteCartItem(cartItemId);

        // Verify that the repository method was called
        verify(cartItemRepository, times(1)).deleteById(cartItemId);

        // Assert the result
        assertFalse(result);
    }

    @Test
    public void testDeleteCartItemByProductSNSuccess() {
        // Mock data
        String serialNumberToDelete = "1";

        // Mock the repository method
        doNothing().when(cartItemRepository).deleteCartItemByproductSN(serialNumberToDelete);

        // Test the service method
        boolean result = cartItemService.deleteCartItemByproductSN(serialNumberToDelete);

        // Verify that the repository method was called
        verify(cartItemRepository, times(1)).deleteCartItemByproductSN(serialNumberToDelete);

        // Assert the result
        assertTrue(result);
    }

    @Test
    public void testDeleteCartItemByProductSNFailure() {
        // Mock data
        String serialNumberToDelete = "1";


        // Mock the repository method to throw an exception
        doThrow(new RuntimeException("Deletion failed")).when(cartItemRepository).deleteCartItemByproductSN(serialNumberToDelete);

        // Test the service method
        boolean result = cartItemService.deleteCartItemByproductSN(serialNumberToDelete);

        // Verify that the repository method was called
        verify(cartItemRepository, times(1)).deleteCartItemByproductSN(serialNumberToDelete);

        // Assert the result
        assertFalse(result);
    }


}
