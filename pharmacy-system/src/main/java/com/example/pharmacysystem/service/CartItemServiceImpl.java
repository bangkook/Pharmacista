package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.repository.CartItemRepository;
import com.example.pharmacysystem.utils.CartItemId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService{
    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public boolean saveCartItem(CartItem item) {
        try {
            cartItemRepository.save(item);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
