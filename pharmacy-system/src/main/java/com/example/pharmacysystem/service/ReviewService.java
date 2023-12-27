package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Review;

import java.util.List;

public interface ReviewService {
    Review saveReview(Review review);
    List<Review> getReviewsForProduct(String productSN);
}
