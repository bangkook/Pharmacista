package com.example.pharmacysystem.repositories;

import com.example.pharmacysystem.model.Review;
import com.example.pharmacysystem.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void getReviewsForProductTest_CheckCorrectness() {
        String productSN1 = "123456789123456858";
        String productSN2 = "987654321987654321";

        Date reviewDate = Date.valueOf(LocalDate.now());

        Review review1 = new Review(1, productSN1, Review.Rate.FOUR_STARS, "Very good product!", reviewDate);
        Review review2 = new Review(2, productSN2, Review.Rate.FOUR_STARS, "Bad Product causes allergies!", reviewDate);

        entityManager.persist(review1);
        entityManager.persist(review2);
        entityManager.flush();

        List<Review> resultProduct1 = reviewRepository.findByProductSN(productSN1);

        // Asserting the result
        assertEquals(1, resultProduct1.size());
        assertEquals(productSN1, resultProduct1.get(0).getProductSN());
        assertEquals(Review.Rate.FOUR_STARS, resultProduct1.get(0).getRating());
        assertEquals("Very good product!", resultProduct1.get(0).getComment());
        assertEquals(reviewDate, resultProduct1.get(0).getReviewDate());

        // Same with productSN2
        List<Review> resultProduct2 = reviewRepository.findByProductSN(productSN2);

        // Asserting the result
        assertEquals(1, resultProduct2.size());
        assertEquals(productSN2, resultProduct2.get(0).getProductSN());
        assertEquals(Review.Rate.FOUR_STARS, resultProduct2.get(0).getRating());
        assertEquals("Bad Product causes allergies!", resultProduct2.get(0).getComment());
        assertEquals(reviewDate, resultProduct2.get(0).getReviewDate());
    }

    @Test
    void getReviewsForProductTest_Empty() {
        String nonExistingProductSN = "nonExistingProduct";

        List<Review> result = reviewRepository.findByProductSN(nonExistingProductSN);

        // Asserting the result
        assertEquals(0, result.size());
    }

}
