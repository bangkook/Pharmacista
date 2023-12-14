package com.example.pharmacysystem.repository;

import com.example.pharmacysystem.model.CartItem;
import com.example.pharmacysystem.utils.CartItemId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    @Query("SELECT c FROM CartItem c WHERE c.userId = :userId AND c.productSN = :productSN")
    CartItem findByUserNameAndSerialNumber(@Param("userId") int userId, @Param("productSN") String productSN);

    List<CartItem> getCartItemsByUserId(int userId);

    void deleteCartItemByproductSN(String SerialNumber);


}
