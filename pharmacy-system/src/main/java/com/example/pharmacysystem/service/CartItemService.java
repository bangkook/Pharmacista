package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.utils.CartItemId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartItemService {

    boolean saveCartItem(CartItem item);

    boolean deleteCartItem(CartItemId cartItemId);
    List<CartItem> getCartItemsByUserId(int userId);


}
