package com.example.pharmacysystem.repository;

import com.example.pharmacysystem.model.FavoriteItem;
import com.example.pharmacysystem.utils.FavoriteItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteItemRepository extends JpaRepository<FavoriteItem, FavoriteItemId> {

    List<FavoriteItem> findByUserId(int userId);

    @Query(value = "SELECT favorite_items.user_id, favorite_items.productsn FROM favorite_items INNER JOIN (SELECT serial_number, product.name FROM product) AS R " +
            "ON favorite_items.productsn = R.serial_number WHERE favorite_items.user_id = ? ORDER BY R.name ASC",
            nativeQuery = true)
    List<FavoriteItem> findByUserIdOrderByProductNameAsc(int userId);

}
