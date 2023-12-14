package com.example.pharmacysystem.services;

import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.repository.CartItemRepository;
import com.example.pharmacysystem.repository.UserRepository;
import com.example.pharmacysystem.service.CartItemService;
import com.example.pharmacysystem.utils.CartItemId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartItemServiceTest {

    @Autowired
    private CartItemService cartItemService;

    @MockBean
    private CartItemRepository cartItemRepository;

    @Test
    public void testSaveCartItem_Success() {
        CartItem itemToSave = new CartItem(1, "SN123", 2);

        when(cartItemRepository.save(itemToSave)).thenReturn(itemToSave);

        Assert.assertTrue(cartItemService.saveCartItem(itemToSave));
    }
    @Test
    public void testSaveCartItem_Null() {
        CartItem itemToSave = new CartItem(1, "SN123", 2);

        when(cartItemRepository.save(itemToSave)).thenReturn(null);

        Assert.assertFalse(cartItemService.saveCartItem(itemToSave));
    }

    @Test
    public void testSaveCartItem_Failure() {
        CartItem itemToSave = new CartItem(1, "SN123", 2);

        when(cartItemRepository.save(itemToSave)).thenThrow(new RuntimeException("Simulated exception"));

        Assert.assertFalse(cartItemService.saveCartItem(itemToSave));
    }

    @Test
    public void testDeleteCartItem_Success() {
        CartItemId cartItemId = new CartItemId(1, "SN123");

        doNothing().when(cartItemRepository).deleteById(cartItemId);

        Assert.assertTrue(cartItemService.deleteCartItem(cartItemId));
    }

    @Test
    public void testDeleteCartItem_Failure() {
        CartItemId cartItemId = new CartItemId(1, "SN123");

        doThrow(new RuntimeException("Simulated exception")).when(cartItemRepository).deleteById(cartItemId);

        Assert.assertFalse(cartItemService.deleteCartItem(cartItemId));
    }

    @Test
    public void testGetCartItemsByUserId_Success() {
        int userId = 1;
        List<CartItem> expectedCartItems = Arrays.asList(
                new CartItem(userId, "SN123", 1),
                new CartItem(userId, "SN222", 2)
        );

        when(cartItemRepository.getCartItemsByUserId(userId)).thenReturn(expectedCartItems);

        List<CartItem> actualCartItems = cartItemService.getCartItemsByUserId(userId);

        Assert.assertEquals(expectedCartItems, actualCartItems);
    }

    @Test
    public void testGetCartItemsByUserId_Exception() {
        int userId = 1;

        when(cartItemRepository.getCartItemsByUserId(userId)).thenThrow(new RuntimeException("Simulated exception"));

        RuntimeException thrownException = Assert.assertThrows(RuntimeException.class, () -> {
            cartItemService.getCartItemsByUserId(userId);
        });

        Assert.assertTrue(thrownException.getMessage().contains("Error retrieving cart items for user ID: " + userId));
    }
}
