package com.example.pharmacysystem.service;

import com.example.pharmacysystem.model.Review;
import com.example.pharmacysystem.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsForProduct(String productSN) {
        return reviewRepository.findByProductSN(productSN);
    }
}