package com.example.pharmacysystem.service;

import com.example.pharmacysystem.dto.ReviewDTO;
import com.example.pharmacysystem.model.Review;

import java.util.List;

public interface ReviewService {
    Review saveReview(Review review);
    List<ReviewDTO> getReviewsForProduct(String productSN);

    ReviewDTO  convertToDTO(Review review);
}
