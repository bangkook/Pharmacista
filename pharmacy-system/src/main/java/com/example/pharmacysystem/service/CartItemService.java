package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.utils.CartItemId;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {

    boolean saveCartItem(CartItem item);
}
