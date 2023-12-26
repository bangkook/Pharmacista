package com.example.pharmacysystem.services;

import com.example.pharmacysystem.model.FavoriteItem;
import com.example.pharmacysystem.repository.FavoriteItemRepository;
import com.example.pharmacysystem.service.FavoriteListService;
import com.example.pharmacysystem.utils.FavoriteItemId;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FavoriteListServiceTest {

    @Autowired
    FavoriteListService favoriteListService;

    @MockBean
    FavoriteItemRepository favoriteItemRepository;


    @Test
    public void addToFavoriteListTest_Successful() {
        int user1 = 1;
        String productSN1 = "abc123";
        FavoriteItem favoriteItem = new FavoriteItem(user1, productSN1);

        // Mocking the repository behavior
        when(favoriteItemRepository.save(favoriteItem)).thenReturn(favoriteItem);
        when(favoriteItemRepository.existsById(any(FavoriteItemId.class))).thenReturn(true);

        boolean isSuccess = favoriteListService.add(favoriteItem);

        assertTrue(isSuccess);
    }

    @Test
    public void addToFavoriteListTest_Failed() {
        int user1 = 1;
        String productSN1 = "abc123";
        FavoriteItem favoriteItem = new FavoriteItem(user1, productSN1);

        // Mocking the repository behavior
        when(favoriteItemRepository.save(favoriteItem)).thenReturn(favoriteItem);
        when(favoriteItemRepository.existsById(any(FavoriteItemId.class))).thenReturn(false);

        boolean isSuccess = favoriteListService.add(favoriteItem);

        assertFalse(isSuccess);
    }

    @Test
    public void addToFavoriteListTest_ThrowException() {
        FavoriteItem favoriteItem = new FavoriteItem();

        // Mocking the repository behavior
        when(favoriteItemRepository.save(favoriteItem)).thenThrow(new SqlScriptException(""));

        boolean isSuccess = favoriteListService.add(favoriteItem);

        assertFalse(isSuccess);
    }

    @Test
    public void deleteFromFavoriteListTest_Successful() {
        int user1 = 1;
        String productSN1 = "abc123";
        FavoriteItem favoriteItem = new FavoriteItem(user1, productSN1);

        // Mocking the repository behavior
        doNothing().when(favoriteItemRepository).delete(favoriteItem);
        when(favoriteItemRepository.existsById(new FavoriteItemId(user1, productSN1))).thenReturn(false);

        boolean isSuccess = favoriteListService.delete(favoriteItem);

        assertTrue(isSuccess);
    }

    @Test
    public void deleteFromFavoriteListTest_Failed() {
        int user1 = 1;
        String productSN1 = "abc123";
        FavoriteItem favoriteItem = new FavoriteItem(user1, productSN1);

        // Mocking the repository behavior
        doNothing().when(favoriteItemRepository).delete(favoriteItem);
        when(favoriteItemRepository.existsById(new FavoriteItemId(user1, productSN1))).thenReturn(true);

        boolean isSuccess = favoriteListService.delete(favoriteItem);

        assertFalse(isSuccess);
    }

    @Test
    public void deleteFromFavoriteListTest_ThrowException() {
        FavoriteItem favoriteItem = new FavoriteItem();

        // Mocking the repository behavior
        doThrow(new SqlScriptException("")).when(favoriteItemRepository).delete(favoriteItem);

        boolean isSuccess = favoriteListService.delete(favoriteItem);

        assertFalse(isSuccess);
    }

    @Test
    public void findByUserIdTest_Exists() {
        int user1 = 1;
        String productSN1 = "abc123", productSN2 = "def456";
        FavoriteItem favoriteItem1 = new FavoriteItem(user1, productSN1);
        FavoriteItem favoriteItem2 = new FavoriteItem(user1, productSN2);

        // Mocking the repository behavior
        when(favoriteItemRepository.findByUserId(user1)).thenReturn(Arrays.asList(favoriteItem1, favoriteItem2));

        List<FavoriteItem> result = favoriteListService.findByUserId(user1);

        assertEquals(result.size(), 2);
        assertTrue(result.contains(favoriteItem1));
        assertTrue(result.contains(favoriteItem2));
    }

    @Test
    public void findByUserIdTest_Empty() {
        int user1 = 1;

        // Mocking the repository behavior
        when(favoriteItemRepository.findByUserId(user1)).thenReturn(Collections.emptyList());

        List<FavoriteItem> result = favoriteListService.findByUserId(user1);

        assertTrue(result.isEmpty());
    }

    @Test
    public void findByUserIdSortedAscTest_Exists() {
        int user1 = 1;
        String productSN1 = "abc123", productSN2 = "def456";
        FavoriteItem favoriteItem1 = new FavoriteItem(user1, productSN1);
        FavoriteItem favoriteItem2 = new FavoriteItem(user1, productSN2);

        // Mocking the repository behavior
        when(favoriteItemRepository.findByUserIdOrderByProductNameAsc(user1)).thenReturn(Arrays.asList(favoriteItem2, favoriteItem1));

        List<FavoriteItem> result = favoriteListService.findByUserIdSorted(user1);
        List<FavoriteItem> expectedResult = Arrays.asList(favoriteItem2, favoriteItem1);

        // Assert
        assertEquals(result.size(), 2);
        assertEquals(result, expectedResult);
    }

    @Test
    public void findByUserIdSortedAscTest_Empty() {
        int user1 = 1;

        // Mocking the repository behavior
        when(favoriteItemRepository.findByUserIdOrderByProductNameAsc(user1)).thenReturn(Collections.emptyList());

        List<FavoriteItem> result = favoriteListService.findByUserIdSorted(user1);

        // Assert
        assertTrue(result.isEmpty());
    }

}
