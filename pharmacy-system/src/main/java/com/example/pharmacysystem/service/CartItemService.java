package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.utils.CartItemId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartItemService {


    void updateQuantityByUserNameAndSerialNumber(int userId, String productSN, int quantity);

    List<CartItem> getCartItemsByUserId(int userId);

    boolean deleteCartItem(CartItemId cartItemId);

    boolean deleteCartItemByproductSN(String SerialNumber);

    boolean saveCartItem(CartItem item);


}
