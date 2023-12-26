package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.FavoriteItem;
import com.example.pharmacysystem.repository.FavoriteItemRepository;
import com.example.pharmacysystem.utils.FavoriteItemId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteListServiceImpl implements FavoriteListService {

    @Autowired
    private FavoriteItemRepository favoriteItemRepository;

    @Override
    public boolean add(FavoriteItem favoriteItem) {
        try {
            favoriteItemRepository.save(favoriteItem);
            return favoriteItemRepository.existsById(new FavoriteItemId(favoriteItem.getUserId(), favoriteItem.getProductSN()));
        } catch (Exception e) {
            System.out.println("Error adding item to favorites");
            return false;
        }
    }

    @Override
    public boolean delete(FavoriteItem favoriteItem) {
        try {
            favoriteItemRepository.delete(favoriteItem);
            return !favoriteItemRepository.existsById(new FavoriteItemId(favoriteItem.getUserId(), favoriteItem.getProductSN()));
        } catch (Exception e) {
            System.out.println("Error deleting item from favorites");
            return false;
        }
    }

    @Override
    public List<FavoriteItem> findByUserId(int userId) {
        return favoriteItemRepository.findByUserId(userId);
    }

    @Override
    public List<FavoriteItem> findByUserIdSorted(int userId, boolean ascending) {
        if (ascending) {
            return favoriteItemRepository.findByUserIdOrderByProductNameAsc(userId);
        }
        return favoriteItemRepository.findByUserIdOrderByProductNameDesc(userId);
    }
}
