package com.example.pharmacysystem.repository;

import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.utils.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
}
