package com.example.pharmacysystem.service;

import com.example.pharmacysystem.dto.ReviewDTO;
import com.example.pharmacysystem.model.Review;
import com.example.pharmacysystem.model.User;
import com.example.pharmacysystem.repository.ReviewRepository;
import com.example.pharmacysystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<ReviewDTO> getReviewsForProduct(String productSN) {
        List<Review> reviewList = reviewRepository.findByProductSN(productSN);
        return reviewList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO convertToDTO(Review review) {
        User user = userRepository.findById(review.getUserId()).orElse(null);
        if (user != null) {
            return new ReviewDTO(
                    review.getReviewId(),
                    review.getUserId(),
                    review.getProductSN(),
                    review.getRating(),
                    review.getComment(),
                    review.getReviewDate(),
                    user.getUsername());
        } else {
            return null;
        }
    }
}