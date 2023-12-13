package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.repository.CartItemRepository;
import com.example.pharmacysystem.repository.ProductRepository;
import com.example.pharmacysystem.utils.CartItemId;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;



    @Override
    public void updateQuantityByUserNameAndSerialNumber(int userId, String productSN, int quantity){
        CartItem cartItem = cartItemRepository.findByUserNameAndSerialNumber(userId, productSN);

        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

    }

    @Override
    public List<CartItem> getCartItemsByUserId(int userId){
        return cartItemRepository.getCartItemsByUserId(userId);
    }

    @Override
    public boolean deleteCartItem(CartItemId cartItemId) {
        try {

            cartItemRepository.deleteById(cartItemId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteCartItemByproductSN(String SerialNumber){
        try {
            cartItemRepository.deleteCartItemByproductSN(SerialNumber);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
