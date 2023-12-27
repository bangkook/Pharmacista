package com.example.pharmacysystem.services;

import com.example.pharmacysystem.dto.ReviewDTO;
import com.example.pharmacysystem.model.Review;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.ReviewRepository;
import com.example.pharmacysystem.repository.UserRepository;
import com.example.pharmacysystem.service.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewServiceTest {
    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ReviewService reviewService;

    @Test
    public void testSaveReview() {
        // Mock data
        Date reviewDate = Date.valueOf(LocalDate.now());
        Review review = new Review(1, "123456789123456858", Review.Rate.FOUR_STARS, "Very good product!", reviewDate);

        // Mock the repository method
        when(reviewRepository.save(review)).thenReturn(review);

        // Test the service method
        Review savedReview = reviewService.saveReview(review);

        // Verify that the repository method was called
        verify(reviewRepository, times(1)).save(review);

        // Assert the result
        assertNotNull(savedReview);
        assertEquals(review, savedReview);

        // Assert on each attribute
        assertEquals(1, savedReview.getUserId());
        assertEquals("123456789123456858", savedReview.getProductSN());
        assertEquals(Review.Rate.FOUR_STARS, savedReview.getRating());
        assertEquals("Very good product!", savedReview.getComment());
        assertEquals(reviewDate, savedReview.getReviewDate());
    }

    @Test
    public void testGetReviewsForProduct() {
        // Mock data
        String productSN = "123456789123456858";
        String anotherProductSN = "987654321987654321";
        Date reviewDate = Date.valueOf(LocalDate.now());
        List<Review> expectedReviews = getReviews(productSN, reviewDate, anotherProductSN);
        int userId1 = 1;
        int userId2 = 3;
        User user1 = new User("user1", "password1", "address1", "city1", "country1", "1234", "01271676366", null);
        User user2 = new User("user2", "password2", "address2", "city2", "country2", "1234", "01271667636", null);
        // Mock the repository method
        when(reviewRepository.findByProductSN(productSN)).thenReturn(expectedReviews);
        when(userRepository.findById(userId1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(userId2)).thenReturn(Optional.of(user2));

        // Test the service method
        List<ReviewDTO> actualReviews = reviewService.getReviewsForProduct(productSN);

        // Verify that the repository method was called
        verify(reviewRepository, times(1)).findByProductSN(productSN);

        // Assert the result
        assertNotNull(actualReviews);
        assertEquals(expectedReviews.size(), actualReviews.size());

        // Assert on each attribute of the first review
        assertEquals(userId1, actualReviews.get(0).getUserId());
        assertEquals(productSN, actualReviews.get(0).getProductSN());
        assertEquals(Review.Rate.FOUR_STARS, actualReviews.get(0).getRating());
        assertEquals("Very good product!", actualReviews.get(0).getComment());
        assertEquals(reviewDate, actualReviews.get(0).getReviewDate());

        // Assert on each attribute of the second review
        assertEquals(userId2, actualReviews.get(1).getUserId());
        assertEquals(productSN, actualReviews.get(1).getProductSN());
        assertEquals(Review.Rate.FOUR_STARS, actualReviews.get(1).getRating());
        assertEquals("Very good product!", actualReviews.get(1).getComment());
        assertEquals(reviewDate, actualReviews.get(1).getReviewDate());
    }

    // Helper method to create a list of reviews
    private static List<Review> getReviews(String productSN, Date reviewDate, String anotherProductSN) {
        Review r1 = new Review(1, productSN, Review.Rate.FOUR_STARS, "Very good product!", reviewDate);
        Review r2 = new Review(2, anotherProductSN, Review.Rate.FOUR_STARS, "Very good product!", reviewDate);
        Review r3 = new Review(3, productSN, Review.Rate.FOUR_STARS, "Very good product!", reviewDate);
        Review r4 = new Review(100, anotherProductSN, Review.Rate.FOUR_STARS, "Very good product!", reviewDate);
        List<Review> reviewList = Arrays.asList(r1, r3);
        return reviewList;
    }
}
