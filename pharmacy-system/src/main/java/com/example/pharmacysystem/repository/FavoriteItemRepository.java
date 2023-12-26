package com.example.pharmacysystem.repository;

import com.example.pharmacysystem.model.FavoriteItem;
import com.example.pharmacysystem.utils.FavoriteItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteItemRepository extends JpaRepository<FavoriteItem, FavoriteItemId> {

    List<FavoriteItem> findByUserId(int userId);

    @Query(value = "SELECT user_id, productsn FROM favorite_items INNER JOIN (SELECT serial_number, name FROM product) " +
            "ON favorite_items.productsn = serial_number WHERE user_id = ? ORDER BY name ASC",
            nativeQuery = true)
    List<FavoriteItem> findByUserIdOrderByProductNameAsc(int userId);

    @Query(value = "SELECT user_id, productsn FROM favorite_items INNER JOIN (SELECT serial_number, name FROM product) " +
            "ON favorite_items.productsn = serial_number WHERE user_id = ? ORDER BY name DESC",
            nativeQuery = true)
    List<FavoriteItem> findByUserIdOrderByProductNameDesc(int userId);
}
