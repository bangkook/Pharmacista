package com.example.pharmacysystem.repositories;

import com.example.pharmacysystem.model.FavoriteItem;
import com.example.pharmacysystem.model.Product;
import com.example.pharmacysystem.repository.FavoriteItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class FavoriteItemRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    FavoriteItemRepository favoriteItemRepository;

    @Test
    void findByUserIdTestCorrect() {
        int user1 = 1, user2 = 2;
        String productSN1 = "abc123", productSN2 = "def456";

        FavoriteItem favoriteItem1 = new FavoriteItem(user1, productSN1);
        FavoriteItem favoriteItem2 = new FavoriteItem(user1, productSN2);
        FavoriteItem favoriteItem3 = new FavoriteItem(user2, productSN1);

        entityManager.persist(favoriteItem1);
        entityManager.persist(favoriteItem2);
        entityManager.persist(favoriteItem3);
        entityManager.flush();

        List<FavoriteItem> resultUser1 = favoriteItemRepository.findByUserId(user1);

        // Asserting the result
        assertEquals(2, resultUser1.size());

        assertTrue(resultUser1.contains(favoriteItem1));
        assertTrue(resultUser1.contains(favoriteItem2));

        // Same with user 2
        List<FavoriteItem> resultUser2 = favoriteItemRepository.findByUserId(user2);

        // Asserting the result
        assertEquals(1, resultUser2.size());

        assertTrue(resultUser2.contains(favoriteItem3));
    }

    @Test
    void findByUserIdTest_Empty() {
        int user = 10;

        List<FavoriteItem> result = favoriteItemRepository.findByUserId(user);

        // Asserting the result
        assertTrue(result.isEmpty());
    }

    @Test
    void findByUserIdOrderByProductNameAscTest() {
        int user1 = 1;
        String productSN1 = "abc123", productSN2 = "def456", productSN3 = "qwe345";

        FavoriteItem favoriteItem1 = new FavoriteItem(user1, productSN1);
        FavoriteItem favoriteItem2 = new FavoriteItem(user1, productSN2);
        FavoriteItem favoriteItem3 = new FavoriteItem(user1, productSN3);

        Product product3 = new Product(productSN1, 2.3F, new Date(2), new Date(2), "product", 5, "third", "url");
        Product product1 = new Product(productSN2, 2.3F, new Date(2), new Date(2), "product", 5, "first", "url");
        Product product2 = new Product(productSN3, 2.3F, new Date(2), new Date(2), "product", 5, "second", "url");

        entityManager.persist(favoriteItem1);
        entityManager.persist(favoriteItem2);
        entityManager.persist(favoriteItem3);
        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.persist(product3);
        entityManager.flush();

        List<FavoriteItem> result = favoriteItemRepository.findByUserIdOrderByProductNameAsc(user1);
        
        // Asserting the result
        assertEquals(3, result.size());

        List<FavoriteItem> expectedResult = Arrays.asList(favoriteItem2, favoriteItem3, favoriteItem1);
        assertEquals(result, expectedResult);
    }
}
