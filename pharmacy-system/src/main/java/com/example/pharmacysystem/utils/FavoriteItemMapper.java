package com.example.pharmacysystem.utils;

import com.example.pharmacysystem.dto.FavoriteItemDTO;
import com.example.pharmacysystem.model.FavoriteItem;
import com.example.pharmacysystem.model.Product;
import org.springframework.stereotype.Component;

@Component
public class FavoriteItemMapper {
    
    public FavoriteItemDTO toDTO(FavoriteItem favoriteItem, Product product) {
        return new FavoriteItemDTO(favoriteItem.getUserId(),
                product.getSerialNumber(),
                product.getName(),
                product.getPrice(),
                product.getPhoto());
    }
}
