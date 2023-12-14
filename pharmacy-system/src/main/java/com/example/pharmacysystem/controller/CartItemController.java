package com.example.pharmacysystem.controller;


import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.repository.CartItemRepository;
import com.example.pharmacysystem.service.CartItemService;
import com.example.pharmacysystem.utils.CartItemId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(value = {"/cartItem"})
@RestController
@CrossOrigin()
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping("/addCartItem")
    public ResponseEntity<String> addItem(@RequestBody CartItem newItem) {
        if (cartItemService.saveCartItem(newItem)) {
            return new ResponseEntity<>("Item added successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to add item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/removeCartItem/{userId}/{productSN}")
    public ResponseEntity<String> deleteCartItem(
            @PathVariable int userId,
            @PathVariable String productSN
    ) {
        CartItemId cartItemId=new CartItemId(userId,productSN);
        System.out.println("id "+userId);
        if (cartItemService.deleteCartItem(cartItemId)) {
            System.out.println("productSn "+productSN);
            return new ResponseEntity<>("CartItem deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("CartItem not found or deletion failed", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/ProductFromCart/{userId}")
    @ResponseBody
    public ResponseEntity<List<String>> getCartItemsByUser(@PathVariable int userId) {
        try {
            // Assuming your CartItemService has a method to get products from the cart
            List<CartItem> cartItems = cartItemService.getCartItemsByUserId(userId);

            List<String> result = cartItems.stream()
                    .map(CartItem::getProductSN)
                    .collect(Collectors.toList());

            System.out.println("result: " + result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

