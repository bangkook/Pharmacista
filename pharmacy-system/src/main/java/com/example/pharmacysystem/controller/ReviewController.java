package com.example.pharmacysystem.controller;

import com.example.pharmacysystem.dto.ReviewDTO;
import com.example.pharmacysystem.model.Review;
import com.example.pharmacysystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/reviews")
@CrossOrigin()
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/save")
    public ResponseEntity<String> saveReview(@RequestBody Review review) {
        try {
            Date reviewDate = Date.valueOf(LocalDate.now());
            review.setReviewDate(reviewDate);
            reviewService.saveReview(review);
            return new ResponseEntity<>("Review saved Successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Couldn't save review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productSN}")
    public ResponseEntity<List<ReviewDTO>> getReviewsForProduct(@PathVariable String productSN) {
        try {
            List<ReviewDTO> reviewDTOList = reviewService.getReviewsForProduct(productSN);
            return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}