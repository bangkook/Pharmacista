package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.FavoriteItem;

import java.util.List;

public interface FavoriteListService {

    boolean add(FavoriteItem favoriteItem);

    boolean delete(FavoriteItem favoriteItem);

    List<FavoriteItem> findByUserId(int userId);

    List<FavoriteItem> findByUserIdSorted(int userId, boolean ascending);

}
